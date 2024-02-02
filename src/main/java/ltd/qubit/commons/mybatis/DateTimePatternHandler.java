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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import javax.annotation.Nullable;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ltd.qubit.commons.lang.DateUtils.getDate;
import static ltd.qubit.commons.lang.DateUtils.getDateTime;

/**
 * Handle the local date time without a timezone.
 *
 * @author Haixing Hu
 */
public class DateTimePatternHandler extends BaseTypeHandler<Date> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DateTimePatternHandler.class);

  private static final String DEFAULT_PATTERN = "yyyy-MM-dd[[' ']['T']HH:mm[':'ss[.SSS]]]";

  private static final DateTimeFormatter DEFAULT_FORMATTER =
      DateTimeFormatter.ofPattern(DEFAULT_PATTERN);

  private String pattern;
  private DateTimeFormatter formatter;

  public DateTimePatternHandler() {
    pattern = DEFAULT_PATTERN;
    formatter = DEFAULT_FORMATTER;
  }

  public DateTimePatternHandler(final String pattern) {
    this.pattern = pattern;
    formatter = DateTimeFormatter.ofPattern(pattern);
  }

  public final String getPattern() {
    return pattern;
  }

  public final DateTimePatternHandler setPattern(final String pattern) {
    this.pattern = pattern;
    formatter = DateTimeFormatter.ofPattern(pattern);
    return this;
  }

  @Override
  public void setNonNullParameter(final PreparedStatement ps, final int i,
          final Date date, final JdbcType jdbcType) throws SQLException {
    if (date == null) {
      ps.setString(i, null);
    } else {
      ps.setString(i, formatDate(date));
    }
  }

  @Override
  public Date getNullableResult(final ResultSet rs, final String columnName)
          throws SQLException {
    final String str = rs.getString(columnName);
    return parseDate(str);
  }

  @Override
  public Date getNullableResult(final ResultSet rs, final int columnIndex)
          throws SQLException {
    final String str = rs.getString(columnIndex);
    return parseDate(str);
  }

  @Override
  public Date getNullableResult(final CallableStatement cs, final int columnIndex)
          throws SQLException {
    final String str = cs.getString(columnIndex);
    return parseDate(str);
  }

  private Date parseDate(@Nullable final String str) {
    LOGGER.debug("Parsing date: {}", str);
    if (str != null) {
      try {
        final TemporalAccessor temporal = formatter.parseBest(str, ZonedDateTime::from,
                LocalDateTime::from, LocalDate::from);
        if (temporal instanceof LocalDate) {
          final LocalDate date = (LocalDate) temporal;
          return getDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        } else if (temporal instanceof LocalDateTime) {
          final LocalDateTime time = (LocalDateTime) temporal;
          return getDateTime(time.getYear(), time.getMonthValue(), time.getDayOfMonth(),
                  time.getHour(), time.getMinute(), time.getSecond());
        } else if (temporal instanceof ZonedDateTime) {
          final ZonedDateTime time = (ZonedDateTime) temporal;
          return Date.from(time.toInstant());
        } else {
          LOGGER.error("Unsupported time type {} while parsing {}", temporal.getClass(), str);
          return null;
        }
      } catch (final DateTimeParseException e) {
        LOGGER.error("Invalid date time format: {}, expected {}", str, pattern);
        return null;
      }
    }
    return null;
  }

  private String formatDate(@Nullable final Date date) {
    if (date == null) {
      return null;
    } else {
      return formatter.format(date.toInstant());
    }
  }
}
