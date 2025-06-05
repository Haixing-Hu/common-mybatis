////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.mybatis;

import org.apache.ibatis.type.MappedTypes;

import ltd.qubit.commons.util.codec.KeyValuePairListCodec;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * {@link KeyValuePairList} 类型的 MyBatis 类型处理器。
 *
 * <p>该处理器将 {@link KeyValuePairList} 类型与 {@link String} 类型进行相互映射。
 *
 * @author 胡海星
 */
@MappedTypes(KeyValuePairList.class)
public class KeyValuePairListHandler extends ObjectCodecHandler<KeyValuePairList> {

  /**
   * 构造一个 {@code KeyValuePairListHandler} 对象。
   */
  public KeyValuePairListHandler() {
    super(new KeyValuePairListCodec());
  }
}