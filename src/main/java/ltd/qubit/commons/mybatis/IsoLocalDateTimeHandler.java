////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.mybatis;

import java.time.LocalDateTime;

import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;

/**
 * Handle the local date time without a timezone. The format of the local data
 * time satisfies the ISO-8601 standard "yyyy-MM-dd HH:mm:ss".
 *
 * @author Haixing Hu
 */
public class IsoLocalDateTimeHandler extends ObjectCodecHandler<LocalDateTime> {

  public IsoLocalDateTimeHandler() {
    super(LocalDateTime.class, new IsoLocalDateTimeCodec());
  }
}
