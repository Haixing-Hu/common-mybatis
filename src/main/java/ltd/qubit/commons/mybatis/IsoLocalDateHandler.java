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
 * 处理不带时区的本地日期。本地日期的格式符合 ISO-8601 标准 "yyyy-MM-dd"。
 *
 * @author 胡海星
 */
public class IsoLocalDateHandler extends ObjectCodecHandler<LocalDate> {

  /**
   * 构造一个 {@code IsoLocalDateHandler} 对象。
   */
  public IsoLocalDateHandler() {
    super(new IsoLocalDateCodec());
  }
}