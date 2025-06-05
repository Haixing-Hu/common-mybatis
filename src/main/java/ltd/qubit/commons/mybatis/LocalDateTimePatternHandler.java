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
import java.time.LocalDateTime;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.LocalDateTimeCodec;

/**
 * {@link LocalDateTime} 类型的 MyBatis 类型处理器，可以处理不带时区的本地日期时间，并支持自定义格式模式。
 *
 * @author 胡海星
 */
public class LocalDateTimePatternHandler extends BaseTypeHandler<LocalDateTime> {

  /**
   * 日志记录器。
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateTimePatternHandler.class);

  /**
   * 用于 {@link LocalDateTime} 和 {@link String} 之间相互转换的编解码器。
   */
  private final LocalDateTimeCodec codec;

  /**
   * 构造一个 {@code LocalDateTimePatternHandler} 对象。
   * <p>
   * 使用默认的日期时间格式模式。
   */
  public LocalDateTimePatternHandler() {
    codec = new LocalDateTimeCodec();
  }

  /**
   * 使用指定的格式模式构造一个 {@code LocalDateTimePatternHandler} 对象。
   *
   * @param pattern
   *     日期时间的格式模式。
   */
  public LocalDateTimePatternHandler(final String pattern) {
    codec = new LocalDateTimeCodec(pattern);
  }

  /**
   * 获取当前使用的日期时间编码格式模式。
   *
   * @return 当前的日期时间编码格式模式。
   */
  public final String getPattern() {
    return codec.getEncodePattern();
  }

  /**
   * 设置日期时间的编码和解码格式模式。
   *
   * @param pattern
   *     要设置的日期时间格式模式。
   * @return 此 {@code LocalDateTimePatternHandler} 对象本身，方便链式调用。
   */
  public final LocalDateTimePatternHandler setPattern(final String pattern) {
    codec.setDecodePatterns(new String[]{ pattern });
    codec.setEncodePattern(pattern);
    return this;
  }

  /**
   * 设置 {@link PreparedStatement} 的非空参数。
   *
   * @param ps
   *     {@link PreparedStatement} 对象。
   * @param i
   *     参数的索引。
   * @param datetime
   *     要设置的 {@link LocalDateTime} 对象。
   * @param jdbcType
   *     参数的 JDBC 类型。
   * @throws SQLException
   *     如果设置参数时发生 SQL 错误。
   */
  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
      final LocalDateTime datetime, final JdbcType jdbcType)
      throws SQLException {
    if (datetime == null) {
      ps.setString(i, null);
    } else {
      ps.setString(i, codec.encode(datetime));
    }
  }

  /**
   * 从 {@link ResultSet} 中根据列名获取可能为空的 {@link LocalDateTime} 结果。
   *
   * @param rs
   *     {@link ResultSet} 对象。
   * @param columnName
   *     列名。
   * @return 从数据库中读取并解码得到的 {@link LocalDateTime} 对象，如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果或解码时发生 SQL 错误。
   */
  @Override
  public LocalDateTime getNullableResult(final ResultSet rs,
      final String columnName) throws SQLException {
    final String str = rs.getString(columnName);
    try {
      return codec.decode(str);
    } catch (final DecodingException e) {
      throw new SQLException(e);
    }
  }

  /**
   * 从 {@link ResultSet} 中根据列索引获取可能为空的 {@link LocalDateTime} 结果。
   *
   * @param rs
   *     {@link ResultSet} 对象。
   * @param columnIndex
   *     列索引。
   * @return 从数据库中读取并解码得到的 {@link LocalDateTime} 对象，如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果或解码时发生 SQL 错误。
   */
  @Override
  public LocalDateTime getNullableResult(final ResultSet rs,
      final int columnIndex) throws SQLException {
    final String str = rs.getString(columnIndex);
    try {
      return codec.decode(str);
    } catch (final DecodingException e) {
      throw new SQLException(e);
    }
  }

  /**
   * 从 {@link CallableStatement} 中根据列索引获取可能为空的 {@link LocalDateTime} 结果。
   *
   * @param cs
   *     {@link CallableStatement} 对象。
   * @param columnIndex
   *     列索引。
   * @return 从数据库中读取并解码得到的 {@link LocalDateTime} 对象，如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果或解码时发生 SQL 错误。
   */
  @Override
  public LocalDateTime getNullableResult(final CallableStatement cs,
      final int columnIndex) throws SQLException {
    final String str = cs.getString(columnIndex);
    try {
      return codec.decode(str);
    } catch (final DecodingException e) {
      throw new SQLException(e);
    }
  }
}