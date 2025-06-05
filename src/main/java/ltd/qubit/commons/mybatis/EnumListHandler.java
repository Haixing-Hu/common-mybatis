////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.mybatis;

import java.util.List;

import ltd.qubit.commons.util.codec.EnumListCodec;

/**
 * {@link Enum} 列表类型的 MyBatis 类型处理器。
 *
 * <p>该处理器将 {@link java.util.List}&lt;E&gt; (其中 E 为枚举类型) 与 {@link String} 类型进行相互映射。
 *
 * @param <E> 列表中的枚举类型。
 * @author 胡海星
 */
public class EnumListHandler<E extends Enum<E>> extends ObjectCodecHandler<List<E>> {

  /**
   * 构造一个 {@code EnumListHandler} 对象。
   *
   * @param enumClass
   *     列表中枚举元素的 {@link Class} 对象。
   */
  public EnumListHandler(final Class<E> enumClass) {
    super(new EnumListCodec<>(enumClass));
  }

}