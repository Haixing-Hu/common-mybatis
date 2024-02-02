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
 * The MyBatis type handler for the {@link Instant} class.
 *
 * <p>It maps Java 8 {@link Instant} &lt;-&gt; {@link Timestamp}
 * with timezone.
 *
 * @author Haixing Hu
 */
@MappedTypes(OffsetDateTime.class)
public class OffsetDateTimeHandler extends BaseTypeHandler<OffsetDateTime> {

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

  @Override
  public OffsetDateTime getNullableResult(final ResultSet rs, final String columnName)
      throws SQLException {
    final Timestamp ts = rs.getTimestamp(columnName, Calendar.getInstance());
    if (ts != null) {
      return OffsetDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());
    }
    return null;
  }

  @Override
  public OffsetDateTime getNullableResult(final ResultSet rs, final int columnIndex)
      throws SQLException {
    final Timestamp ts = rs.getTimestamp(columnIndex, Calendar.getInstance());
    if (ts != null) {
      return OffsetDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());
    }
    return null;
  }

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
