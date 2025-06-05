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
 * 处理不带时区的本地日期时间。本地日期时间的格式符合 ISO-8601 标准 "yyyy-MM-dd HH:mm:ss"。
 *
 * @author 胡海星
 */
public class IsoLocalDateTimeHandler extends ObjectCodecHandler<LocalDateTime> {

  /**
   * 构造一个 {@code IsoLocalDateTimeHandler} 对象。
   */
  public IsoLocalDateTimeHandler() {
    super(new IsoLocalDateTimeCodec());
  }
}