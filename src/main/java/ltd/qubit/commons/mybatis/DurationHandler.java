////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.mybatis;

import java.time.Duration;

import org.apache.ibatis.type.MappedTypes;

import ltd.qubit.commons.util.codec.DurationCodec;

/**
 * The MyBatis type handler for the {@link Duration} class.
 *
 * <p>It maps Java 8 {@link Duration} &lt;-&gt; {@link String}.
 *
 * @author Haixing Hu
 */
@MappedTypes(Duration.class)
public class DurationHandler extends ObjectCodecHandler<Duration> {

  public DurationHandler() {
    super(new DurationCodec());
  }
}
