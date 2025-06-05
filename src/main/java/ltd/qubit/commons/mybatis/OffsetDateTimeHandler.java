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
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/**
 * {@link OffsetDateTime} 类型的 MyBatis 类型处理器。
 * <p>
 * 该处理器将 Java 8 的 {@link OffsetDateTime} 类型与带时区信息的 {@link java.sql.Timestamp}
 * 类型进行相互映射。存入数据库时，会保留 {@link OffsetDateTime} 的时区偏移量。
 * 从数据库读出时，会使用系统默认时区将时间戳转换为 {@link OffsetDateTime}。
 *
 * @author 胡海星
 */
@MappedTypes(OffsetDateTime.class)
public class OffsetDateTimeHandler extends BaseTypeHandler<OffsetDateTime> {

  /**
   * 设置 {@link PreparedStatement} 的非空参数。
   * <p>
   * 将 {@link OffsetDateTime} 参数转换为 {@link java.sql.Timestamp}，并使用
   * {@link OffsetDateTime} 的时区偏移量关联的 {@link java.util.Calendar} 对象
   * 设置到 {@link PreparedStatement} 中。
   *
   * @param ps
   *     {@link PreparedStatement} 对象。
   * @param i
   *     参数的索引。
   * @param parameter
   *     要设置的 {@link OffsetDateTime} 对象。
   * @param jdbcType
   *     参数的 JDBC 类型。
   * @throws SQLException
   *     如果设置参数时发生 SQL 错误。
   */
  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
      final OffsetDateTime parameter, final JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      ps.setTimestamp(i, null);
    } else {
      ps.setTimestamp(i, Timestamp.from(parameter.toInstant()),
          GregorianCalendar.from(parameter.toZonedDateTime()));
    }
  }

  /**
   * 从 {@link ResultSet} 中根据列名获取可能为空的 {@link OffsetDateTime} 结果。
   * <p>
   * 从数据库读取时间戳时，使用 {@link Calendar#getInstance()} (通常是系统默认时区)
   * 获取时间戳，然后使用系统默认时区 ({@link ZoneId#systemDefault()}) 将其转换为
   * {@link OffsetDateTime} 对象。
   *
   * @param rs
   *     {@link ResultSet} 对象。
   * @param columnName
   *     列名。
   * @return 从数据库中读取并转换得到的 {@link OffsetDateTime} 对象，如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果时发生 SQL 错误。
   */
  @Override
  public OffsetDateTime getNullableResult(final ResultSet rs, final String columnName)
      throws SQLException {
    final Timestamp ts = rs.getTimestamp(columnName, Calendar.getInstance());
    if (ts != null) {
      return OffsetDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());
    }
    return null;
  }

  /**
   * 从 {@link ResultSet} 中根据列索引获取可能为空的 {@link OffsetDateTime} 结果。
   * <p>
   * 从数据库读取时间戳时，使用 {@link Calendar#getInstance()} (通常是系统默认时区)
   * 获取时间戳，然后使用系统默认时区 ({@link ZoneId#systemDefault()}) 将其转换为
   * {@link OffsetDateTime} 对象。
   *
   * @param rs
   *     {@link ResultSet} 对象。
   * @param columnIndex
   *     列索引。
   * @return 从数据库中读取并转换得到的 {@link OffsetDateTime} 对象，如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果时发生 SQL 错误。
   */
  @Override
  public OffsetDateTime getNullableResult(final ResultSet rs, final int columnIndex)
      throws SQLException {
    final Timestamp ts = rs.getTimestamp(columnIndex, Calendar.getInstance());
    if (ts != null) {
      return OffsetDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());
    }
    return null;
  }

  /**
   * 从 {@link CallableStatement} 中根据列索引获取可能为空的 {@link OffsetDateTime} 结果。
   * <p>
   * 从数据库读取时间戳时，使用 {@link Calendar#getInstance()} (通常是系统默认时区)
   * 获取时间戳，然后使用系统默认时区 ({@link ZoneId#systemDefault()}) 将其转换为
   * {@link OffsetDateTime} 对象。
   *
   * @param cs
   *     {@link CallableStatement} 对象。
   * @param columnIndex
   *     列索引。
   * @return 从数据库中读取并转换得到的 {@link OffsetDateTime} 对象，如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果时发生 SQL 错误。
   */
  @Override
  public OffsetDateTime getNullableResult(final CallableStatement cs, final int columnIndex)
      throws SQLException {
    final Timestamp ts = cs.getTimestamp(columnIndex, Calendar.getInstance());
    if (ts != null) {
      return OffsetDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());
    }
    return null;
  }
}