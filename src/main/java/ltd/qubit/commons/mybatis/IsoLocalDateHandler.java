////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.mybatis;

import java.time.LocalDate;

import ltd.qubit.commons.util.codec.IsoLocalDateCodec;

/**
 * Handle the local date without a timezone. The format of the local data satisfies
 * the ISO-8601 standard "yyyy-MM-dd".
 *
 * @author Haixing Hu
 */
public class IsoLocalDateHandler extends ObjectCodecHandler<LocalDate> {

  public IsoLocalDateHandler() {
    super(new IsoLocalDateCodec());
  }
}
