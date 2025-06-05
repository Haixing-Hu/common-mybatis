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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import ltd.qubit.commons.lang.DateUtils;

/**
 * {@link java.util.Date} 类型的 MyBatis 类型处理器，用于处理 UTC 时间戳。
 * <p>
 * 该处理器确保从数据库读取时间戳时使用 UTC 时区，并将 {@link java.util.Date} 对象
 * 作为 UTC 时间戳存入数据库。
 *
 * @author 胡海星
 */
@MappedTypes(Date.class)
public class UtcDateHandler extends BaseTypeHandler<Date> {

  /**
   * UTC 时区常量。
   */
  private static final TimeZone UTC = DateUtils.UTC;

  /**
   * 设置 {@link PreparedStatement} 的非空参数。
   * <p>
   * 如果日期对象不为 {@code null}，则使用 UTC 时区将其转换为 {@link java.sql.Timestamp}
   * 并设置到 {@link PreparedStatement} 中。
   *
   * @param preparedStatement
   *     {@link PreparedStatement} 对象。
   * @param i
   *     参数的索引。
   * @param date
   *     要设置的 {@link java.util.Date} 对象。
   * @param jdbcType
   *     参数的 JDBC 类型。
   * @throws SQLException
   *     如果设置参数时发生 SQL 错误。
   */
  @Override
  public void setNonNullParameter(final PreparedStatement preparedStatement,
          final int i, final Date date, final JdbcType jdbcType)
          throws SQLException {
    if (date == null) {
      preparedStatement.setNull(i, jdbcType.TYPE_CODE);
    } else {
      final Timestamp timestamp = new Timestamp(date.getTime());
      preparedStatement.setTimestamp(i, timestamp, getUtcCalendar());
    }
  }

  /**
   * 从 {@link ResultSet} 中根据列名获取可能为空的 {@link java.util.Date} 结果。
   * <p>
   * 从数据库读取时间戳时，使用 UTC 时区进行转换。
   *
   * @param resultSet
   *     {@link ResultSet} 对象。
   * @param s
   *     列名。
   * @return 从数据库中读取并使用 UTC 时区转换得到的 {@link java.util.Date} 对象，
   *         如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果时发生 SQL 错误。
   */
  @Override
  public Date getNullableResult(final ResultSet resultSet,
          final String s) throws SQLException {
    return resultSet.getTimestamp(s, getUtcCalendar());
  }

  /**
   * 从 {@link ResultSet} 中根据列索引获取可能为空的 {@link java.util.Date} 结果。
   * <p>
   * 从数据库读取时间戳时，使用 UTC 时区进行转换。
   *
   * @param resultSet
   *     {@link ResultSet} 对象。
   * @param i
   *     列索引。
   * @return 从数据库中读取并使用 UTC 时区转换得到的 {@link java.util.Date} 对象，
   *         如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果时发生 SQL 错误。
   */
  @Override
  public Date getNullableResult(final ResultSet resultSet,
          final int i) throws SQLException {
    return resultSet.getTimestamp(i, getUtcCalendar());
  }

  /**
   * 从 {@link CallableStatement} 中根据列索引获取可能为空的 {@link java.util.Date} 结果。
   * <p>
   * 从数据库读取时间戳时，使用 UTC 时区进行转换。
   *
   * @param callableStatement
   *     {@link CallableStatement} 对象。
   * @param i
   *     列索引。
   * @return 从数据库中读取并使用 UTC 时区转换得到的 {@link java.util.Date} 对象，
   *         如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果时发生 SQL 错误。
   */
  @Override
  public Date getNullableResult(final CallableStatement callableStatement,
          final int i) throws SQLException {
    return callableStatement.getTimestamp(i, getUtcCalendar());
  }

  /**
   * 获取一个表示 UTC 时区的 {@link Calendar} 实例。
   *
   * @return 表示 UTC 时区的 {@link Calendar} 实例。
   */
  private static Calendar getUtcCalendar() {
    return Calendar.getInstance(UTC);
  }
}