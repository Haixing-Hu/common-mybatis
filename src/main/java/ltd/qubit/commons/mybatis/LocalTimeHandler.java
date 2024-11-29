////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.mybatis;

import java.sql.Time;
import java.time.LocalTime;

import org.apache.ibatis.type.MappedTypes;

import ltd.qubit.commons.util.codec.LocalTimeCodec;

/**
 * The MyBatis type handler for the {@link LocalTime} class.
 *
 * <p>It maps Java 8 {@link LocalTime} &lt;-&gt; {@link Time}
 *
 * @author Haixing Hu
 */
@MappedTypes(LocalTime.class)
public class LocalTimeHandler extends ObjectCodecHandler<LocalTime> {

  public LocalTimeHandler() {
    super(LocalTime.class, new LocalTimeCodec());
  }
}
