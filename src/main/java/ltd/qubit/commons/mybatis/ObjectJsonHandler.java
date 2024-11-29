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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;

/**
 * The MyBatis type handler for any class.
 * <p>
 * It maps the object &lt;-&gt; {@link String}, using JSON format.
 * <p>
 * This class is intended to be used as a base class for implementing the type
 * handlers of specific classes.
 *
 * @author Haixing Hu
 */
public class ObjectJsonHandler<T> extends BaseTypeHandler<T> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final Class<T> type;
  private final JsonMapper mapper = new CustomizedJsonMapper();

  protected ObjectJsonHandler(final Class<T> type) {
    this.type = type;
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
      return mapper.writeValueAsString(obj);
    } catch (final JsonProcessingException e) {
      logger.error("An error occurs while encoding the object into JSON string: {}", obj, e);
      throw new SQLException(e);
    }
  }

  private T decode(final String str) throws SQLException {
    if (str != null) {
      try {
        return mapper.readValue(str, type);
      } catch (final JsonProcessingException e) {
        logger.error("An error occurs while decoding the object from JSON string: '{}'", str, e);
        throw new SQLException(e);
      }
    }
    return null;
  }
}
