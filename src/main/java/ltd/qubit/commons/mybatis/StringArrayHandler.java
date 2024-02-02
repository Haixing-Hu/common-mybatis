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

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.EncodingException;
import ltd.qubit.commons.util.codec.StringArrayCodec;

/**
 * The MyBatis type handler for the {@code String[]} class.
 *
 * <p>It maps {@code String[]} &lt;-&gt; {@code String}, using comma to separate
 * elements in the string array.
 *
 * @author Haixing Hu
 */
@MappedTypes(String[].class)
public class StringArrayHandler extends BaseTypeHandler<String[]> {

  private final Logger logger = LoggerFactory.getLogger(StringArrayHandler.class);
  private final StringArrayCodec codec = new StringArrayCodec();

  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int columnIndex,
      final String[] parameter, final JdbcType type) throws SQLException {
    if (parameter == null) {
      ps.setString(columnIndex, null);
    } else {
      try {
        ps.setString(columnIndex, codec.encode(parameter));
      } catch (final EncodingException e) {
        logger.error("An error occurs: {}", e.getMessage(), e);
        throw new SQLException(e);
      }
    }
  }

  @Override
  public String[] getNullableResult(final ResultSet rs, final String columnName)
          throws SQLException {
    final String str = rs.getString(columnName);
    if (str != null) {
      try {
        return codec.decode(str);
      } catch (final DecodingException e) {
        logger.error("An error occurs: {}", e.getMessage(), e);
        throw new SQLException(e);
      }
    }
    return null;
  }

  @Override
  public String[] getNullableResult(final ResultSet rs, final int columnIndex)
          throws SQLException {
    final String str = rs.getString(columnIndex);
    if (str != null) {
      try {
        return codec.decode(str);
      } catch (final DecodingException e) {
        logger.error("An error occurs: {}", e.getMessage(), e);
        throw new SQLException(e);
      }
    }
    return null;
  }

  @Override
  public String[] getNullableResult(final CallableStatement cs, final int columnIndex)
          throws SQLException {
    final String str = cs.getString(columnIndex);
    if (str != null) {
      try {
        return codec.decode(str);
      } catch (final DecodingException e) {
        logger.error("An error occurs: {}", e.getMessage(), e);
        throw new SQLException(e);
      }
    }
    return null;
  }
}
