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

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/**
 * {@link Instant} 类型的 MyBatis 类型处理器。
 * <p>
 * 该处理器将 Java 8 的 {@link Instant} 类型与 {@link java.sql.Timestamp} 类型进行相互映射。
 * {@link Instant} 表示 UTC 时间线上的一个瞬时点，不包含时区信息。
 *
 * @author 胡海星
 */
@MappedTypes(Instant.class)
public class InstantHandler extends BaseTypeHandler<Instant> {

  /**
   * 设置 {@link PreparedStatement} 的非空参数。
   * <p>
   * 将 {@link Instant} 参数转换为 {@link java.sql.Timestamp} 并设置到
   * {@link PreparedStatement} 中。
   *
   * @param ps
   *     {@link PreparedStatement} 对象。
   * @param i
   *     参数的索引。
   * @param parameter
   *     要设置的 {@link Instant} 对象。
   * @param jdbcType
   *     参数的 JDBC 类型 (此处未使用)。
   * @throws SQLException
   *     如果设置参数时发生 SQL 错误。
   */
  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
      final Instant parameter, final JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      ps.setTimestamp(i, null);
    } else {
      ps.setTimestamp(i, Timestamp.from(parameter));
    }
  }

  /**
   * 从 {@link ResultSet} 中根据列名获取可能为空的 {@link Instant} 结果。
   * <p>
   * 从数据库读取 {@link java.sql.Timestamp} 并将其转换为 {@link Instant}。
   *
   * @param rs
   *     {@link ResultSet} 对象。
   * @param columnName
   *     列名。
   * @return 从数据库中读取并转换得到的 {@link Instant} 对象，如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果时发生 SQL 错误。
   */
  @Override
  public Instant getNullableResult(final ResultSet rs, final String columnName)
      throws SQLException {
    final Timestamp ts = rs.getTimestamp(columnName);
    if (ts != null) {
      return ts.toInstant();
    }
    return null;
  }

  /**
   * 从 {@link ResultSet} 中根据列索引获取可能为空的 {@link Instant} 结果。
   * <p>
   * 从数据库读取 {@link java.sql.Timestamp} 并将其转换为 {@link Instant}。
   *
   * @param rs
   *     {@link ResultSet} 对象。
   * @param columnIndex
   *     列索引。
   * @return 从数据库中读取并转换得到的 {@link Instant} 对象，如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果时发生 SQL 错误。
   */
  @Override
  public Instant getNullableResult(final ResultSet rs, final int columnIndex)
      throws SQLException {
    final Timestamp ts = rs.getTimestamp(columnIndex);
    if (ts != null) {
      return ts.toInstant();
    }
    return null;
  }

  /**
   * 从 {@link CallableStatement} 中根据列索引获取可能为空的 {@link Instant} 结果。
   * <p>
   * 从数据库读取 {@link java.sql.Timestamp} 并将其转换为 {@link Instant}。
   *
   * @param cs
   *     {@link CallableStatement} 对象。
   * @param columnIndex
   *     列索引。
   * @return 从数据库中读取并转换得到的 {@link Instant} 对象，如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果时发生 SQL 错误。
   */
  @Override
  public Instant getNullableResult(final CallableStatement cs, final int columnIndex)
      throws SQLException {
    final Timestamp ts = cs.getTimestamp(columnIndex);
    if (ts != null) {
      return ts.toInstant();
    }
    return null;
  }
}