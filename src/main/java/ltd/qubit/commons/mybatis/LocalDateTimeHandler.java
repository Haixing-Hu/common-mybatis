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

import org.apache.ibatis.type.MappedTypes;

import ltd.qubit.commons.util.codec.LocalDateTimeCodec;

/**
 * {@link LocalDateTime} 类型的 MyBatis 类型处理器。
 *
 * <p>该处理器将 Java 8 的 {@link LocalDateTime} 类型与 {@link java.sql.Timestamp} 类型进行相互映射。
 *
 * <p><b>注意：</b> 为了避免时区问题，我们在存入数据库时将 {@link LocalDateTime} 转换为
 * {@link String}，反之亦然。</p>
 *
 * @author 胡海星
 */
@MappedTypes(LocalDateTime.class)
public class LocalDateTimeHandler extends ObjectCodecHandler<LocalDateTime> {

  /**
   * 构造一个 {@code LocalDateTimeHandler} 对象。
   */
  public LocalDateTimeHandler() {
    super(new LocalDateTimeCodec());
  }
}