package com.sts.sinorita.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class CommonUtil {
  public static boolean isInCommaText(String commaText, String subtext, Character seperator) {
    if (!commaText.isEmpty() || !subtext.isEmpty())
      return false;
    int len = subtext.length();
    if (len > commaText.length())
      return false;
    int index = commaText.indexOf(subtext);
    while (index >= 0) {
      if ((index == 0 || commaText.charAt(index - 1) == seperator)
          && (index + len >= commaText.length() || commaText.charAt(index + len) == seperator))
        return true;
      if (index + len >= commaText.length())
        break;
      index = commaText.indexOf(subtext, index + len + 1);
    }
    return false;
  }

  public static <T> T nvl(T val, T defaultVal) {
    if (val == null)
      return defaultVal;
    return val;
  }

  public static <T> boolean isEmpty(T[] array) {
    return array == null || array.length == 0;
  }

  public static <T> boolean isEmpty(Collection<T> c) {
    return (c == null || c.isEmpty());
  }

  public static <T> boolean isNotEmpty(T[] paramArrayOfT) {
    return paramArrayOfT != null && paramArrayOfT.length != 0;
  }

  public static <T> boolean isNotEmpty(Collection<T> c) {
    return (c != null && !c.isEmpty());
  }

  public static boolean eitherEmpty(String first, String second) {
    return (StringUtil.isEmpty(first) || StringUtil.isEmpty(second));
  }

  public static Map<String, String> analyticComplexAttrValue(String attrValue, String firstSplit, String secondSplit) {
    Map<String, String> attrValueMap = new HashMap<>();
    if (StringUtil.isEmpty(attrValue) || StringUtil.isEmpty(firstSplit) || StringUtil.isEmpty(secondSplit))
      return attrValueMap;
    String[] firstSplitedStrs = attrValue.split(firstSplit);
    if (isEmpty(firstSplitedStrs))
      return attrValueMap;
    analyticComplexAttrValue(attrValueMap, firstSplitedStrs, secondSplit);
    return attrValueMap;
  }

  private static void analyticComplexAttrValue(Map<String, String> attrValueMap, String[] firstSplitedStrs,
      String secondSplit) {
    String[] secondSplitedStrs = null;
    for (int i = 0; i < firstSplitedStrs.length; i++) {
      if (!StringUtil.isEmpty(firstSplitedStrs[i])) {
        secondSplitedStrs = firstSplitedStrs[i].split(secondSplit);
        if (secondSplitedStrs != null && secondSplitedStrs.length >= 2)
          attrValueMap.put(secondSplitedStrs[0], secondSplitedStrs[1]);
      }
    }
  }

  public static boolean isInCommaText(String commaText, String subtext) {
    return isInCommaText(commaText, subtext, ',');
  }

  
}
