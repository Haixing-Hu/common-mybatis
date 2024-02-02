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
 * The MyBatis type handler for the {@link Instant} class.
 *
 * <p>It maps Java 8 {@link Instant} &lt;-&gt; {@link Timestamp}.
 *
 * @author Haixing Hu
 */
@MappedTypes(Instant.class)
public class InstantHandler extends BaseTypeHandler<Instant> {

  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
      final Instant parameter, final JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      ps.setTimestamp(i, null);
    } else {
      ps.setTimestamp(i, Timestamp.from(parameter));
    }
  }

  @Override
  public Instant getNullableResult(final ResultSet rs, final String columnName)
      throws SQLException {
    final Timestamp ts = rs.getTimestamp(columnName);
    if (ts != null) {
      return ts.toInstant();
    }
    return null;
  }

  @Override
  public Instant getNullableResult(final ResultSet rs, final int columnIndex)
      throws SQLException {
    final Timestamp ts = rs.getTimestamp(columnIndex);
    if (ts != null) {
      return ts.toInstant();
    }
    return null;
  }

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
