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

import ltd.qubit.commons.datastructure.list.StringList;
import ltd.qubit.commons.util.codec.StringListCodec;

/**
 * {@link StringList} 类型的 MyBatis 类型处理器。
 *
 * <p>该处理器将 {@link StringList} 类型与 {@link String} 类型进行相互映射。
 *
 * @author 胡海星
 */
@MappedTypes(StringList.class)
public class StringListHandler extends ObjectCodecHandler<StringList> {

  public StringListHandler() {
    super(new StringListCodec());
  }

}