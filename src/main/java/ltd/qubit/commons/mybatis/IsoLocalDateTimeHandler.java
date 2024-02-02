////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.mybatis;

/**
 * Handle the local date time without a timezone. The format of the local data
 * time satisfies the ISO-8601 standard "yyyy-MM-dd HH:mm:ss".
 *
 * @author Haixing Hu
 */
public class IsoLocalDateTimeHandler extends LocalDateTimePatternHandler {

  public IsoLocalDateTimeHandler() {
    super("yyyy-MM-dd[[' ']['T']HH:mm[':'ss[.SSS]]]");
  }
}
