////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import javax.annotation.Nullable;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ltd.qubit.commons.lang.DateUtils.getDate;
import static ltd.qubit.commons.lang.DateUtils.getDateTime;

/**
 * {@link java.util.Date} 类型的 MyBatis 类型处理器，支持自定义日期时间格式模式。
 * <p>
 * 该处理器将 {@link java.util.Date} 对象与数据库中的字符串表示进行相互映射。
 * <ul>
 *   <li>在将 {@link java.util.Date} 存入数据库时，会将其转换为 UTC 时区的 {@link java.time.Instant}，
 *       然后使用指定的格式模式 ({@link #pattern}) 进行格式化。</li>
 *   <li>在从数据库读取字符串时，会尝试使用指定的格式模式将其解析为
 *       {@link java.time.ZonedDateTime}、{@link java.time.LocalDateTime} 或
 *       {@link java.time.LocalDate}，然后转换为 {@link java.util.Date} 对象。</li>
 * </ul>
 * 默认格式模式为 "yyyy-MM-dd[[' ']['T']HH:mm[':'ss[.SSS]]]"，
 * 该模式可以灵活匹配日期和可选的时间部分。
 *
 * @author 胡海星
 */
public class DateTimePatternHandler extends BaseTypeHandler<Date> {

  /**
   * 日志记录器。
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DateTimePatternHandler.class);

  /**
   * 默认的日期时间格式模式。
   * <p>
   * 格式为 "yyyy-MM-dd[[' ']['T']HH:mm[':'ss[.SSS]]]"，可以解析常见的 ISO 日期时间格式，
   * 包括：
   * <ul>
   *   <li>yyyy-MM-dd</li>
   *   <li>yyyy-MM-dd HH:mm</li>
   *   <li>yyyy-MM-dd HH:mm:ss</li>
   *   <li>yyyy-MM-dd HH:mm:ss.SSS</li>
   *   <li>yyyy-MM-ddTHH:mm</li>
   *   <li>yyyy-MM-ddTHH:mm:ss</li>
   *   <li>yyyy-MM-ddTHH:mm:ss.SSS</li>
   * </ul>
   * 方括号表示可选部分。
   */
  private static final String DEFAULT_PATTERN = "yyyy-MM-dd[[' ']['T']HH:mm[':'ss[.SSS]]]";

  /**
   * 基于 {@link #DEFAULT_PATTERN} 的默认日期时间格式化器。
   */
  private static final DateTimeFormatter DEFAULT_FORMATTER =
      DateTimeFormatter.ofPattern(DEFAULT_PATTERN);

  /**
   * 当前使用的日期时间格式模式字符串。
   */
  private String pattern;
  /**
   * 基于当前 {@link #pattern} 的日期时间格式化器。
   */
  private DateTimeFormatter formatter;

  /**
   * 构造一个 {@code DateTimePatternHandler} 对象。
   * <p>
   * 使用 {@link #DEFAULT_PATTERN} 作为默认的日期时间格式模式。
   */
  public DateTimePatternHandler() {
    pattern = DEFAULT_PATTERN;
    formatter = DEFAULT_FORMATTER;
  }

  /**
   * 使用指定的格式模式构造一个 {@code DateTimePatternHandler} 对象。
   *
   * @param pattern
   *     自定义的日期时间格式模式。
   * @throws IllegalArgumentException 如果提供的模式无效。
   */
  public DateTimePatternHandler(final String pattern) {
    this.pattern = pattern;
    formatter = DateTimeFormatter.ofPattern(pattern);
  }

  /**
   * 获取当前使用的日期时间格式模式。
   *
   * @return 当前的日期时间格式模式字符串。
   */
  public final String getPattern() {
    return pattern;
  }

  /**
   * 设置新的日期时间格式模式。
   *
   * @param pattern
   *     要设置的新的日期时间格式模式字符串。
   * @return 此 {@code DateTimePatternHandler} 对象本身，方便链式调用。
   * @throws IllegalArgumentException 如果提供的模式无效。
   */
  public final DateTimePatternHandler setPattern(final String pattern) {
    this.pattern = pattern;
    formatter = DateTimeFormatter.ofPattern(pattern);
    return this;
  }

  /**
   * 设置 {@link PreparedStatement} 的非空参数。
   * <p>
   * 将 {@link java.util.Date} 对象使用当前格式模式 ({@link #formatter}) 格式化为字符串，
   * 并设置到 {@link PreparedStatement} 中。格式化时，{@link java.util.Date} 会先转换为
   * UTC 时区的 {@link java.time.Instant}。
   *
   * @param ps
   *     {@link PreparedStatement} 对象。
   * @param i
   *     参数的索引。
   * @param date
   *     要设置的 {@link java.util.Date} 对象。
   * @param jdbcType
   *     参数的 JDBC 类型 (此处未使用)。
   * @throws SQLException
   *     如果设置参数时发生 SQL 错误。
   */
  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
          final Date date, final JdbcType jdbcType) throws SQLException {
    if (date == null) {
      ps.setString(i, null);
    } else {
      ps.setString(i, formatDate(date));
    }
  }

  /**
   * 从 {@link ResultSet} 中根据列名获取可能为空的 {@link java.util.Date} 结果。
   *
   * @param rs
   *     {@link ResultSet} 对象。
   * @param columnName
   *     列名。
   * @return 从数据库中读取字符串并使用当前格式模式解析得到的 {@link java.util.Date} 对象，
   *         如果值为 SQL NULL 或解析失败，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果时发生 SQL 错误。
   */
  @Override
  public Date getNullableResult(final ResultSet rs, final String columnName)
          throws SQLException {
    final String str = rs.getString(columnName);
    return parseDate(str);
  }

  /**
   * 从 {@link ResultSet} 中根据列索引获取可能为空的 {@link java.util.Date} 结果。
   *
   * @param rs
   *     {@link ResultSet} 对象。
   * @param columnIndex
   *     列索引。
   * @return 从数据库中读取字符串并使用当前格式模式解析得到的 {@link java.util.Date} 对象，
   *         如果值为 SQL NULL 或解析失败，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果时发生 SQL 错误。
   */
  @Override
  public Date getNullableResult(final ResultSet rs, final int columnIndex)
          throws SQLException {
    final String str = rs.getString(columnIndex);
    return parseDate(str);
  }

  /**
   * 从 {@link CallableStatement} 中根据列索引获取可能为空的 {@link java.util.Date} 结果。
   *
   * @param cs
   *     {@link CallableStatement} 对象。
   * @param columnIndex
   *     列索引。
   * @return 从数据库中读取字符串并使用当前格式模式解析得到的 {@link java.util.Date} 对象，
   *         如果值为 SQL NULL 或解析失败，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果时发生 SQL 错误。
   */
  @Override
  public Date getNullableResult(final CallableStatement cs, final int columnIndex)
          throws SQLException {
    final String str = cs.getString(columnIndex);
    return parseDate(str);
  }

  /**
   * 使用当前格式模式 ({@link #formatter}) 解析日期时间字符串。
   * <p>
   * 会依次尝试将输入字符串解析为 {@link java.time.ZonedDateTime}、
   * {@link java.time.LocalDateTime} 或 {@link java.time.LocalDate}。
   * 解析成功后，转换为 {@link java.util.Date} 对象。
   *
   * @param str
   *     要解析的日期时间字符串，可以为 {@code null}。
   * @return 解析成功后转换得到的 {@link java.util.Date} 对象；
   *         如果输入字符串为 {@code null} 或解析失败，则返回 {@code null}。
   */
  private Date parseDate(@Nullable final String str) {
    LOGGER.debug("Parsing date: {}", str);
    if (str != null) {
      try {
        final TemporalAccessor temporal = formatter.parseBest(str, ZonedDateTime::from,
                LocalDateTime::from, LocalDate::from);
        if (temporal instanceof LocalDate) {
          final LocalDate date = (LocalDate) temporal;
          return getDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        } else if (temporal instanceof LocalDateTime) {
          final LocalDateTime time = (LocalDateTime) temporal;
          return getDateTime(time.getYear(), time.getMonthValue(), time.getDayOfMonth(),
                  time.getHour(), time.getMinute(), time.getSecond());
        } else if (temporal instanceof ZonedDateTime) {
          final ZonedDateTime time = (ZonedDateTime) temporal;
          return Date.from(time.toInstant());
        } else {
          LOGGER.error("Unsupported time type {} while parsing {}", temporal.getClass(), str);
          return null;
        }
      } catch (final DateTimeParseException e) {
        LOGGER.error("Invalid date time format: {}, expected {}", str, pattern);
        return null;
      }
    }
    return null;
  }

  /**
   * 使用当前格式模式 ({@link #formatter}) 格式化 {@link java.util.Date} 对象。
   * <p>
   * {@link java.util.Date} 对象会先转换为 UTC 时区的 {@link java.time.Instant}，
   * 然后进行格式化。
   *
   * @param date
   *     要格式化的 {@link java.util.Date} 对象，可以为 {@code null}。
   * @return 格式化后的日期时间字符串；如果输入日期为 {@code null}，则返回 {@code null}。
   */
  private String formatDate(@Nullable final Date date) {
    if (date == null) {
      return null;
    } else {
      return formatter.format(date.toInstant());
    }
  }
}