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
 * {@link Duration} 类型的 MyBatis 类型处理器。
 *
 * <p>该处理器将 Java 8 的 {@link Duration} 类型与 {@link String} 类型进行相互映射。
 *
 * @author 胡海星
 */
@MappedTypes(Duration.class)
public class DurationHandler extends ObjectCodecHandler<Duration> {

  /**
   * 构造一个 {@code DurationHandler} 对象。
   */
  public DurationHandler() {
    super(new DurationCodec());
  }
}