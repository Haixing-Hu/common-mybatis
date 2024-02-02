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
import java.time.LocalDateTime;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.LocalDateTimeCodec;

/**
 * Handle the local date time without a timezone.
 *
 * @author Haixing Hu
 */
public class LocalDateTimePatternHandler extends BaseTypeHandler<LocalDateTime> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateTimePatternHandler.class);
  private static final String DEFAULT_PATTERN = LocalDateTimeCodec.DEFAULT_DECODE_PATTERN;

  private final LocalDateTimeCodec codec;

  public LocalDateTimePatternHandler() {
    codec = new LocalDateTimeCodec();
  }

  public LocalDateTimePatternHandler(final String pattern) {
    codec = new LocalDateTimeCodec(pattern);
  }

  public final String getPattern() {
    return codec.getDecodePattern();
  }

  public final LocalDateTimePatternHandler setPattern(final String pattern) {
    codec.setDecodePattern(pattern);
    codec.setEncodePattern(pattern);
    return this;
  }

  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
      final LocalDateTime datetime, final JdbcType jdbcType)
      throws SQLException {
    if (datetime == null) {
      ps.setString(i, null);
    } else {
      ps.setString(i, codec.encode(datetime));
    }
  }

  @Override
  public LocalDateTime getNullableResult(final ResultSet rs,
      final String columnName) throws SQLException {
    final String str = rs.getString(columnName);
    try {
      return codec.decode(str);
    } catch (final DecodingException e) {
      throw new SQLException(e);
    }
  }

  @Override
  public LocalDateTime getNullableResult(final ResultSet rs,
      final int columnIndex) throws SQLException {
    final String str = rs.getString(columnIndex);
    try {
      return codec.decode(str);
    } catch (final DecodingException e) {
      throw new SQLException(e);
    }
  }

  @Override
  public LocalDateTime getNullableResult(final CallableStatement cs,
      final int columnIndex) throws SQLException {
    final String str = cs.getString(columnIndex);
    try {
      return codec.decode(str);
    } catch (final DecodingException e) {
      throw new SQLException(e);
    }
  }
}
