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

import javax.annotation.Nullable;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.util.codec.Codec;
import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.EncodingException;

/**
 * The MyBatis type handler for any class.
 * <p>
 * It maps the object &lt;-&gt; {@link String}, using the specified codec.
 * <p>
 * This class is intended to be used as a base class for implementing the type
 * handlers of specific classes.
 *
 * @author Haixing Hu
 */
public class ObjectCodecHandler<T> extends BaseTypeHandler<T> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final Codec<T, String> codec;

  protected ObjectCodecHandler(final Codec<T, String> codec) {
    this.codec = codec;
  }

  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
      final T obj, final JdbcType jdbcType) throws SQLException {
    final String str = encode(obj);
    ps.setString(i, str);
  }

  @Override
  public T getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
    final String str = rs.getString(columnName);
    return decode(str);
  }

  @Override
  public T getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
    final String str = rs.getString(columnIndex);
    return decode(str);
  }

  @Override
  public T getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
    final String str = cs.getString(columnIndex);
    return decode(str);
  }

  private String encode(@Nullable final T obj) throws SQLException {
    if (obj == null) {
      return null;
    }
    try {
      return codec.encode(obj);
    } catch (final EncodingException e) {
      logger.error("An error occurs while encoding the object into string: {}", obj, e);
      throw new SQLException(e);
    }
  }

  private T decode(final String str) throws SQLException {
    if (str != null) {
      try {
        return codec.decode(str);
      } catch (final DecodingException e) {
        logger.error("An error occurs while decoding the object from string: '{}'", str, e);
        throw new SQLException(e);
      }
    }
    return null;
  }
}
