////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.mybatis;

import java.time.Period;

import org.apache.ibatis.type.MappedTypes;

import ltd.qubit.commons.util.codec.PeriodCodec;

/**
 * The MyBatis type handler for the {@link Period} class.
 *
 * <p>It maps Java 8 {@link Period} &lt;-&gt; {@link String}.
 *
 * @author Haixing Hu
 */
@MappedTypes(Period.class)
public class PeriodHandler extends ObjectCodecHandler<Period> {

  public PeriodHandler() {
    super(Period.class, new PeriodCodec());
  }
}
