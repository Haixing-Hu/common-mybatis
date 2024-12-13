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
 * The MyBatis type handler for the list of {@link Enum} class.
 *
 * <p>It maps {@link List<Enum>} &lt;-&gt; {@link String}
 *
 * @author Haixing Hu
 */
public class EnumListHandler<E extends Enum<E>> extends ObjectCodecHandler<List<E>> {

  public EnumListHandler(final Class<E> enumClass) {
    super(new EnumListCodec<>(enumClass));
  }

}
