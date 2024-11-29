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
 * The MyBatis type handler for the {@link StringList} class.
 *
 * <p>It maps {@link StringList} &lt;-&gt; {@link String}
 *
 * @author Haixing Hu
 */
@MappedTypes(StringList.class)
public class StringListHandler extends ObjectCodecHandler<StringList> {

  public StringListHandler() {
    super(StringList.class, new StringListCodec());
  }

}
