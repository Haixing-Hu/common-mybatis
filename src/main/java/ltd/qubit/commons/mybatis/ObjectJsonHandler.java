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
 * 任意类型的 MyBatis 类型处理器。
 * <p>
 * 该处理器使用 JSON 格式将对象与 {@link String} 类型进行相互映射。
 * <p>
 * 此类旨在作为实现特定类的类型处理器的基类。
 *
 * @param <T> 被处理的对象类型。
 * @author 胡海星
 */
public class ObjectJsonHandler<T> extends BaseTypeHandler<T> {

  /**
   * 日志记录器。
   */
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  /**
   * 被处理对象的 {@link Class} 对象。
   */
  private final Class<T> type;
  /**
   * 用于 JSON 序列化和反序列化的 Jackson {@link JsonMapper} 对象。
   */
  private final JsonMapper mapper = new CustomizedJsonMapper();

  /**
   * 构造一个 {@code ObjectJsonHandler} 对象。
   *
   * @param type
   *     被处理对象的 {@link Class} 对象。
   */
  protected ObjectJsonHandler(final Class<T> type) {
    this.type = type;
  }

  /**
   * 设置非空参数。
   *
   * @param ps
   *     PreparedStatement 对象。
   * @param i
   *     参数索引。
   * @param obj
   *     要设置的参数对象。
   * @param jdbcType
   *     参数的 JDBC 类型。
   * @throws SQLException
   *     如果发生 SQL 异常。
   */
  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
      final T obj, final JdbcType jdbcType) throws SQLException {
    final String str = encode(obj);
    ps.setString(i, str);
  }

  /**
   * 从结果集中根据列名获取可能为空的结果。
   *
   * @param rs
   *     ResultSet 对象。
   * @param columnName
   *     列名。
   * @return 从结果集中解码得到的对象，可能为 {@code null}。
   * @throws SQLException
   *     如果发生 SQL 异常。
   */
  @Override
  public T getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
    final String str = rs.getString(columnName);
    return decode(str);
  }

  /**
   * 从结果集中根据列索引获取可能为空的结果。
   *
   * @param rs
   *     ResultSet 对象。
   * @param columnIndex
   *     列索引。
   * @return 从结果集中解码得到的对象，可能为 {@code null}。
   * @throws SQLException
   *     如果发生 SQL 异常。
   */
  @Override
  public T getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
    final String str = rs.getString(columnIndex);
    return decode(str);
  }

  /**
   * 从 CallableStatement 中根据列索引获取可能为空的结果。
   *
   * @param cs
   *     CallableStatement 对象。
   * @param columnIndex
   *     列索引。
   * @return 从 CallableStatement 中解码得到的对象，可能为 {@code null}。
   * @throws SQLException
   *     如果发生 SQL 异常。
   */
  @Override
  public T getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
    final String str = cs.getString(columnIndex);
    return decode(str);
  }

  /**
   * 将对象编码为 JSON 字符串。
   *
   * @param obj
   *     要编码的对象，可以为 {@code null}。
   * @return 编码后的 JSON 字符串；如果输入对象为 {@code null}，则返回 {@code null}。
   * @throws SQLException
   *     如果编码过程中发生错误。
   */
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

  /**
   * 将 JSON 字符串解码为对象。
   *
   * @param str
   *     要解码的 JSON 字符串，可以为 {@code null}。
   * @return 解码后的对象；如果输入字符串为 {@code null}，则返回 {@code null}。
   * @throws SQLException
   *     如果解码过程中发生错误。
   */
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