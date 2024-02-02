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
 * The MyBatis type handler for UTC timestamps.
 *
 * @author Haixing Hu
 */
@MappedTypes(Date.class)
public class UtcDateHandler extends BaseTypeHandler<Date> {

  private static final TimeZone UTC = DateUtils.UTC;

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

  @Override
  public Date getNullableResult(final ResultSet resultSet,
          final String s) throws SQLException {
    return resultSet.getTimestamp(s, getUtcCalendar());
  }

  @Override
  public Date getNullableResult(final ResultSet resultSet,
          final int i) throws SQLException {
    return resultSet.getTimestamp(i, getUtcCalendar());
  }

  @Override
  public Date getNullableResult(final CallableStatement callableStatement,
          final int i) throws SQLException {
    return callableStatement.getTimestamp(i, getUtcCalendar());
  }

  private static Calendar getUtcCalendar() {
    return Calendar.getInstance(UTC);
  }
}
