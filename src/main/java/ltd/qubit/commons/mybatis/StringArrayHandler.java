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
 * The MyBatis type handler for the {@code String[]} class.
 *
 * <p>It maps {@code String[]} &lt;-&gt; {@code String}, using comma to separate
 * elements in the string array.
 *
 * @author Haixing Hu
 */
@MappedTypes(String[].class)
public class StringArrayHandler extends ObjectCodecHandler<String[]> {

  public StringArrayHandler() {
    super(new StringArrayCodec());
  }
}
