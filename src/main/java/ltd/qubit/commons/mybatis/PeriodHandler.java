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
 * {@link Period} 类型的 MyBatis 类型处理器。
 *
 * <p>该处理器将 Java 8 的 {@link Period} 类型与 {@link String} 类型进行相互映射。
 *
 * @author 胡海星
 */
@MappedTypes(Period.class)
public class PeriodHandler extends ObjectCodecHandler<Period> {

  public PeriodHandler() {
    super(new PeriodCodec());
  }
}