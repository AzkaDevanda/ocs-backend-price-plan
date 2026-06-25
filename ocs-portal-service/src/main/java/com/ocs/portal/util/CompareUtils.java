package com.ocs.portal.util;

public class CompareUtils {
//  private static ZSmartLogger logger = ZSmartLogger.getLogger(CompareUtils.class);

  private static final int INSERTIONSORT_THRESHOLD = 7;

  private static final String typeOfObject = null;

  private static final String paramInBo = null;

  public static boolean withinIgnoreCase (String value, String... values) {
    if (values == null || values.length == 0)
      return false;
    for (int i = 0; i < values.length; i++) {
      if (values[i].equalsIgnoreCase(value))
        return true;
    }
    return false;
  }
}
