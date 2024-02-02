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
import java.sql.Time;
import java.time.LocalTime;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/**
 * The MyBatis type handler for the {@link LocalTime} class.
 *
 * <p>It maps Java 8 {@link LocalTime} &lt;-&gt; {@link Time}
 *
 * @author Haixing Hu
 */
@MappedTypes(LocalTime.class)
public class LocalTimeHandler extends BaseTypeHandler<LocalTime> {

  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
      final LocalTime parameter, final JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      ps.setTime(i, null);
    } else {
      ps.setTime(i, Time.valueOf(parameter));
    }
  }

  @Override
  public LocalTime getNullableResult(final ResultSet rs, final String columnName)
      throws SQLException {
    final Time time = rs.getTime(columnName);
    if (time != null) {
      return time.toLocalTime();
    }
    return null;
  }

  @Override
  public LocalTime getNullableResult(final ResultSet rs, final int columnIndex)
      throws SQLException {
    final Time time = rs.getTime(columnIndex);
    if (time != null) {
      return time.toLocalTime();
    }
    return null;
  }

  @Override
  public LocalTime getNullableResult(final CallableStatement cs,
      final int columnIndex) throws SQLException {
    final Time time = cs.getTime(columnIndex);
    if (time != null) {
      return time.toLocalTime();
    }
    return null;
  }
}
