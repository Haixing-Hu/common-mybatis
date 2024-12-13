////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.mybatis;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.ibatis.type.MappedTypes;

import ltd.qubit.commons.util.codec.LocalDateTimeCodec;

/**
 * The MyBatis type handler for the {@link LocalDateTime} class.
 *
 * <p>It maps Java 8 {@link LocalDateTime} &lt;-&gt; {@link Timestamp}.
 *
 * <p><b>NOTE:</b> In order to avoid the timezone problem, we convert the
 * {@link LocalDateTime} to {@link String} to store it to the database,
 * and vice verse.</p>
 *
 * @author Haixing Hu
 */
@MappedTypes(LocalDateTime.class)
public class LocalDateTimeHandler extends ObjectCodecHandler<LocalDateTime> {

  public LocalDateTimeHandler() {
    super(new LocalDateTimeCodec());
  }
}
