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
import java.time.Period;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import ltd.qubit.commons.util.codec.Codec;
import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.EncodingException;
import ltd.qubit.commons.util.codec.PeriodCodec;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The MyBatis type handler for the {@link Period} class.
 *
 * <p>It maps Java 8 {@link Period} &lt;-&gt; {@link String}.
 *
 * @author Haixing Hu
 */
@MappedTypes(Period.class)
public class PeriodHandler extends BaseTypeHandler<Period> {

  private final Codec<Period, String> codec;

  public PeriodHandler() {
    this(new PeriodCodec());
  }

  public PeriodHandler(final Codec<Period, String> codec) {
    this.codec = requireNonNull("codec", codec);
  }

  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
      final Period parameter, final JdbcType jdbcType) throws SQLException {
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
  public Period getNullableResult(final ResultSet rs, final String columnName)
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
  public Period getNullableResult(final ResultSet rs, final int columnIndex)
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
  public Period getNullableResult(final CallableStatement cs,
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
