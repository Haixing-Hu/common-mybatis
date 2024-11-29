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
 * The MyBatis type handler for {@link LocalTimeRange} of List.
 *
 * @author pino
 */
@MappedTypes(LocalTimeRange.class)
public class LocalTimeRangeListHandler extends BaseTypeHandler<LocalTimeRangeList> {

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
