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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/**
 * The MyBatis type handler for the {@link LocalDate} class.
 *
 * <p>It maps Java 8 {@link LocalDate} &lt;-&gt; {@link Date}.
 *
 * <p><b>NOTE:</b> In order to avoid the timezone problem, we convert the
 * {@link LocalDate} to {@link String} to store it to the database,
 * and vice verse.</p>
 *
 * @author Haixing Hu
 */
@MappedTypes(LocalDate.class)
public class LocalDateHandler extends BaseTypeHandler<LocalDate> {

  private static final LocalTime ZERO = LocalTime.of(0, 0);

  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
      final LocalDate parameter, final JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      ps.setString(i, null);
    } else {
      final String date = parameter.toString();
      ps.setString(i, date);
    }
  }

  @Override
  public LocalDate getNullableResult(final ResultSet rs, final String columnName)
      throws SQLException {
    final String date = rs.getString(columnName);
    return stringToLocalDate(date);
  }

  @Override
  public LocalDate getNullableResult(final ResultSet rs, final int columnIndex)
      throws SQLException {
    final String date = rs.getString(columnIndex);
    return stringToLocalDate(date);
  }

  @Override
  public LocalDate getNullableResult(final CallableStatement cs, final int columnIndex)
      throws SQLException {
    final String date = cs.getString(columnIndex);
    return stringToLocalDate(date);
  }

  private LocalDate stringToLocalDate(final String date) {
    if (date == null) {
      return null;
    } else {
      return LocalDate.parse(date);
    }
  }
}
