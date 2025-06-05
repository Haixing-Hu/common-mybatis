////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.mybatis;

import java.time.LocalTime;

import org.apache.ibatis.type.MappedTypes;

import ltd.qubit.commons.util.codec.LocalTimeCodec;

/**
 * {@link LocalTime} 类型的 MyBatis 类型处理器。
 *
 * <p>该处理器将 Java 8 的 {@link LocalTime} 类型与 {@link java.sql.Time} 类型进行相互映射。
 *
 * @author 胡海星
 */
@MappedTypes(LocalTime.class)
public class LocalTimeHandler extends ObjectCodecHandler<LocalTime> {

  /**
   * 构造一个 {@code LocalTimeHandler} 对象。
   */
  public LocalTimeHandler() {
    super(new LocalTimeCodec());
  }
}