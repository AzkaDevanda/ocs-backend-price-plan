package com.ocs.portal.util;

public final class EqualsUtil {
  public static boolean equals (Object a, Object b) {
    if (a == b)
      return true;
    if (a == null || b == null)
      return false;
    return a.equals(b);
  }
}
