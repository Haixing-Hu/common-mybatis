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

import ltd.qubit.commons.datastructure.list.StringList;
import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.EncodingException;
import ltd.qubit.commons.util.codec.StringListCodec;

/**
 * The MyBatis type handler for the {@link StringList} class.
 *
 * <p>It maps {@link StringList} &lt;-&gt; {@link String}
 *
 * @author Haixing Hu
 */
@MappedTypes(StringList.class)
public class StringListHandler extends BaseTypeHandler<StringList> {

  private final Logger logger = LoggerFactory.getLogger(StringListHandler.class);
  private final StringListCodec codec = new StringListCodec();

  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int columnIndex,
          final StringList parameter, final JdbcType jdbcType) throws SQLException {
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
  public StringList getNullableResult(final ResultSet rs, final String columnName)
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
  public StringList getNullableResult(final ResultSet rs, final int columnIndex)
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
  public StringList getNullableResult(final CallableStatement cs, final int columnIndex)
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
