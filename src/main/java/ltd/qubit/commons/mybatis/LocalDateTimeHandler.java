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
import java.time.LocalDateTime;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/**
 * The MyBatis type handler for the {@link LocalDateTime} class.
 *
 * <p>It maps Java 8 {@link LocalDateTime} &lt;-&gt; {@link Timestamp}.
 *
 * <p><b>NOTE:</b> In order to avoid the timezone problem, we convert the
 * {@link LocalDateTime} to {@link String} to store it to the database,
 * and vice verse.</p>
 *
 * @author Haixing Hu
 */
@MappedTypes(LocalDateTime.class)
public class LocalDateTimeHandler extends BaseTypeHandler<LocalDateTime> {

  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
      final LocalDateTime parameter, final JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      ps.setString(i, null);
    } else {
      final String datetime = parameter.toString();
      ps.setString(i, datetime);
    }
  }

  @Override
  public LocalDateTime getNullableResult(final ResultSet rs, final String columnName)
      throws SQLException {
    final String datetime = rs.getString(columnName);
    return stringToLocalDate(datetime);
  }

  @Override
  public LocalDateTime getNullableResult(final ResultSet rs, final int columnIndex)
      throws SQLException {
    final String datetime = rs.getString(columnIndex);
    return stringToLocalDate(datetime);
  }

  @Override
  public LocalDateTime getNullableResult(final CallableStatement cs,
      final int columnIndex) throws SQLException {
    final String datetime = cs.getString(columnIndex);
    return stringToLocalDate(datetime);
  }

  private LocalDateTime stringToLocalDate(final String date) {
    if (date == null) {
      return null;
    } else {
      return LocalDateTime.parse(date);
    }
  }
}
