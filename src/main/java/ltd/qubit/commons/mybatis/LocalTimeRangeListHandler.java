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
import java.time.LocalTime;
import java.util.stream.Collectors;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import ltd.qubit.commons.util.range.LocalTimeRange;
import ltd.qubit.commons.util.range.LocalTimeRangeList;

/**
 * {@link LocalTimeRangeList} 类型的 MyBatis 类型处理器。
 * <p>
 * 该处理器将 {@link LocalTimeRangeList} 对象 ({@link LocalTimeRange} 的列表)
 * 与数据库中的字符串表示进行相互映射。
 * 字符串的格式为 "HH:mm:ss-HH:mm:ss,HH:mm:ss-HH:mm:ss,..."。
 *
 * @author 胡海星
 */
@MappedTypes(LocalTimeRange.class)
public class LocalTimeRangeListHandler extends BaseTypeHandler<LocalTimeRangeList> {

  /**
   * 设置 {@link PreparedStatement} 的非空参数。
   * <p>
   * 将 {@link LocalTimeRangeList} 转换为 "HH:mm:ss-HH:mm:ss,..." 格式的字符串。
   *
   * @param preparedStatement
   *     {@link PreparedStatement} 对象。
   * @param i
   *     参数的索引。
   * @param localTimeRanges
   *     要设置的 {@link LocalTimeRangeList} 对象。
   * @param jdbcType
   *     参数的 JDBC 类型。
   * @throws SQLException
   *     如果设置参数时发生 SQL 错误。
   */
  @Override
  public void setNonNullParameter(final PreparedStatement preparedStatement,
      final int i,
      final LocalTimeRangeList localTimeRanges, final JdbcType jdbcType)
      throws SQLException {
    final String value = localTimeRanges
        .stream()
        .map(it -> it.getStart() + "-" + it.getEnd())
        .collect(Collectors.joining(","));
    preparedStatement.setString(i, value);
  }

  /**
   * 从 {@link ResultSet} 中根据列名获取可能为空的 {@link LocalTimeRangeList} 结果。
   *
   * @param resultSet
   *     {@link ResultSet} 对象。
   * @param s
   *     列名。
   * @return 从数据库中读取并解码得到的 {@link LocalTimeRangeList} 对象，如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果或解码时发生 SQL 错误。
   */
  @Override
  public LocalTimeRangeList getNullableResult(final ResultSet resultSet,
      final String s)
      throws SQLException {
    final String value = resultSet.getString(s);
    if (value != null) {
      return changeString(value);
    } else {
      return null;
    }
  }

  /**
   * 从 {@link ResultSet} 中根据列索引获取可能为空的 {@link LocalTimeRangeList} 结果。
   *
   * @param resultSet
   *     {@link ResultSet} 对象。
   * @param i
   *     列索引。
   * @return 从数据库中读取并解码得到的 {@link LocalTimeRangeList} 对象，如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果或解码时发生 SQL 错误。
   */
  @Override
  public LocalTimeRangeList getNullableResult(final ResultSet resultSet,
      final int i)
      throws SQLException {
    final String value = resultSet.getString(i);
    if (value != null) {
      return changeString(value);
    } else {
      return null;
    }
  }

  /**
   * 从 {@link CallableStatement} 中根据列索引获取可能为空的 {@link LocalTimeRangeList} 结果。
   *
   * @param callableStatement
   *     {@link CallableStatement} 对象。
   * @param i
   *     列索引。
   * @return 从数据库中读取并解码得到的 {@link LocalTimeRangeList} 对象，如果值为 SQL NULL，则返回 {@code null}。
   * @throws SQLException
   *     如果获取结果或解码时发生 SQL 错误。
   */
  @Override
  public LocalTimeRangeList getNullableResult(
      final CallableStatement callableStatement, final int i)
      throws SQLException {
    final String value = callableStatement.getString(i);
    if (value != null) {
      return changeString(value);
    } else {
      return null;
    }
  }

  /**
   * 将 "HH:mm:ss-HH:mm:ss,..." 格式的字符串转换为 {@link LocalTimeRangeList} 对象。
   *
   * @param s
   *     要转换的字符串。
   * @return 转换后的 {@link LocalTimeRangeList} 对象。
   * @throws java.time.format.DateTimeParseException 如果时间字符串格式不正确。
   */
  private LocalTimeRangeList changeString(final String s) {
    final LocalTimeRangeList rts = new LocalTimeRangeList();
    final String[] ranges = s.split(",");
    for (final String range : ranges) {
      final String[] times = range.split("-");
      rts.add(new LocalTimeRange(LocalTime.parse(times[0]),
          LocalTime.parse(times[1])));
    }
    return rts;
  }
}
