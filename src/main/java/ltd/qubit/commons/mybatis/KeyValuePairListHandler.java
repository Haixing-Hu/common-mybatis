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
import ltd.qubit.commons.util.codec.KeyValuePairListCodec;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * The MyBatis type handler for the {@link KeyValuePairList} class.
 *
 * <p>It maps {@link KeyValuePairList} &lt;-&gt; {@link String}
 *
 * @author Haixing Hu
 */
@MappedTypes(KeyValuePairList.class)
public class KeyValuePairListHandler extends BaseTypeHandler<KeyValuePairList> {

  private final Logger logger = LoggerFactory.getLogger(KeyValuePairListHandler.class);
  private final KeyValuePairListCodec codec = new KeyValuePairListCodec();

  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
      final KeyValuePairList params, final JdbcType jdbcType)
      throws SQLException {
    if (params == null) {
      ps.setString(i, null);
    } else {
      try {
        ps.setString(i, codec.encode(params));
      } catch (final EncodingException e) {
        logger.error("An error occurs: {}", e.getMessage(), e);
        throw new SQLException(e);
      }
    }
  }

  @Override
  public KeyValuePairList getNullableResult(final ResultSet rs,
      final String columnName) throws SQLException {
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
  public KeyValuePairList getNullableResult(final ResultSet rs,
      final int columnIndex) throws SQLException {
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
  public KeyValuePairList getNullableResult(final CallableStatement cs,
      final int columnIndex) throws SQLException {
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
