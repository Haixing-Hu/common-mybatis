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
import java.time.Duration;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import ltd.qubit.commons.util.codec.Codec;
import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.DurationCodec;
import ltd.qubit.commons.util.codec.EncodingException;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The MyBatis type handler for the {@link Duration} class.
 *
 * <p>It maps Java 8 {@link Duration} &lt;-&gt; {@link String}.
 *
 * @author Haixing Hu
 */
@MappedTypes(Duration.class)
public class DurationHandler extends BaseTypeHandler<Duration> {

  private final Codec<Duration, String> codec;

  public DurationHandler() {
    this(new DurationCodec());
  }

  public DurationHandler(final Codec<Duration, String> codec) {
    this.codec = requireNonNull("codec", codec);
  }

  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
      final Duration parameter, final JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      ps.setString(i, null);
    } else {
      try {
        final String str = codec.encode(parameter);
        ps.setString(i, str);
      } catch (final EncodingException e) {
        throw new SQLException(e);
      }
    }
  }

  @Override
  public Duration getNullableResult(final ResultSet rs, final String columnName)
      throws SQLException {
    final String str = rs.getString(columnName);
    if (str != null) {
      try {
        return codec.decode(str);
      } catch (final DecodingException e) {
        throw new SQLException(e);
      }
    }
    return null;
  }

  @Override
  public Duration getNullableResult(final ResultSet rs, final int columnIndex)
      throws SQLException {
    final String str = rs.getString(columnIndex);
    if (str != null) {
      try {
        return codec.decode(str);
      } catch (final DecodingException e) {
        throw new SQLException(e);
      }
    }
    return null;
  }

  @Override
  public Duration getNullableResult(final CallableStatement cs,
      final int columnIndex)
      throws SQLException {
    final String str = cs.getString(columnIndex);
    if (str != null) {
      try {
        return codec.decode(str);
      } catch (final DecodingException e) {
        throw new SQLException(e);
      }
    }
    return null;
  }
}
