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

import ltd.qubit.commons.util.codec.StringArrayCodec;

/**
 * {@code String[]} 类型的 MyBatis 类型处理器。
 *
 * <p>该处理器将 {@code String[]} 类型与 {@code String} 类型进行相互映射，
 * 在字符串中使用逗号分隔数组元素。
 *
 * @author 胡海星
 */
@MappedTypes(String[].class)
public class StringArrayHandler extends ObjectCodecHandler<String[]> {

  public StringArrayHandler() {
    super(new StringArrayCodec());
  }
}