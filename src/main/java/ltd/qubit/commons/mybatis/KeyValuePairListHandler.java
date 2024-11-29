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
 * The MyBatis type handler for the {@link KeyValuePairList} class.
 *
 * <p>It maps {@link KeyValuePairList} &lt;-&gt; {@link String}
 *
 * @author Haixing Hu
 */
@MappedTypes(KeyValuePairList.class)
public class KeyValuePairListHandler extends ObjectCodecHandler<KeyValuePairList> {

  public KeyValuePairListHandler() {
    super(KeyValuePairList.class, new KeyValuePairListCodec());
  }
}
