////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.mybatis;

import java.sql.Date;
import java.time.LocalDate;

import org.apache.ibatis.type.MappedTypes;

import ltd.qubit.commons.util.codec.LocalDateCodec;

/**
 * The MyBatis type handler for the {@link LocalDate} class.
 *
 * <p>It maps Java 8 {@link LocalDate} &lt;-&gt; {@link Date}.
 *
 * <p><b>NOTE:</b> In order to avoid the timezone problem, we convert the
 * {@link LocalDate} to {@link String} to store it to the database,
 * and vice verse.</p>
 *
 * @author Haixing Hu
 */
@MappedTypes(LocalDate.class)
public class LocalDateHandler extends ObjectCodecHandler<LocalDate> {

  public LocalDateHandler() {
    super(new LocalDateCodec());
  }
}
