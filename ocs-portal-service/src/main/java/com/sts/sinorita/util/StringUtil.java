package com.sts.sinorita.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringUtil {

  public static String replace(String source, String oldString, String newString) {
    if (source.isEmpty() || oldString.isEmpty())
      return source;
    StringBuilder output = new StringBuilder();
    int lengthOfSource = source.length();
    int lengthOfOld = oldString.length();
    int posStart = 0;
    int pos = source.indexOf(oldString, posStart);
    while (pos >= 0) {
      output.append(source.substring(posStart, pos));
      output.append(newString);
      posStart = pos + lengthOfOld;
      pos = source.indexOf(oldString, posStart);
    }
    if (posStart < lengthOfSource)
      output.append(source.substring(posStart));
    return output.toString();
  }

  public static String padRight(String s, int totalWidth, char paddingChar) {
    StringBuffer sb = new StringBuffer();
    sb.append(s);
    for (int i = 0; i < totalWidth - s.length(); i++)
      sb.append(paddingChar);
    return sb.toString();
  }

  public static String objToString(Object obj) {
    return (obj == null) ? "" : obj.toString();
  }

  public static String toString(Object val) {
    if (val == null)
      return "";
    if (val instanceof long[])
      return arrayToString((long[]) val);
    if (val instanceof int[])
      return arrayToString((int[]) val);
    if (val instanceof short[])
      return arrayToString((short[]) val);
    if (val instanceof byte[])
      return arrayToString((byte[]) val);
    if (val instanceof char[])
      return arrayToString((char[]) val);
    if (val instanceof double[])
      return arrayToString((double[]) val);
    if (val instanceof float[])
      return arrayToString((float[]) val);
    if (val instanceof boolean[])
      return arrayToString((boolean[]) val);
    if (val.getClass().isArray())
      return arrayToString((Object[]) val);
    if (val instanceof List)
      return listToString((List) val, null);
    return val.toString();
  }

  public static String arrayToString(Object[] objs) {
    if (!ValidateUtil.validateNotEmpty(objs))
      return "";
    int size = objs.length;
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size; i++)
      buff.append(String.valueOf(objs[i])).append("\n");
    return buff.toString();
  }

  public static <E> String listToString(List<E> list, String itemName) {
    if (!ValidateUtil.validateNotEmpty(list))
      return "";
    int size = list.size();
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size; i++)
      buff.append(list.get(i).toString()).append("\n");
    return buff.toString();
  }

  public static String arrayToString(long[] objs) {
    int size = objs.length;
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size; i++)
      buff.append("\nItem[").append(i).append("]\n").append(objs[i]);
    return buff.toString();
  }

  public static String arrayToString(int[] objs) {
    int size = objs.length;
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size; i++)
      buff.append("\nItem[").append(i).append("]\n").append(objs[i]);
    return buff.toString();
  }

  public static String arrayToString(short[] objs) {
    int size = objs.length;
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size; i++)
      buff.append("\nItem[").append(i).append("]\n").append(objs[i]);
    return buff.toString();
  }

  public static String arrayToString(byte[] objs) {
    int size = objs.length;
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size; i++)
      buff.append("\nItem[").append(i).append("]\n").append(objs[i]);
    return buff.toString();
  }

  public static String arrayToString(char[] objs) {
    int size = objs.length;
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size; i++)
      buff.append("\nItem[").append(i).append("]\n").append(objs[i]);
    return buff.toString();
  }

  public static String arrayToString(double[] objs) {
    int size = objs.length;
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size; i++)
      buff.append("\nItem[").append(i).append("]\n").append(objs[i]);
    return buff.toString();
  }

  public static String arrayToString(float[] objs) {
    int size = objs.length;
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size; i++)
      buff.append("\nItem[").append(i).append("]\n").append(objs[i]);
    return buff.toString();
  }

  public static String arrayToString(boolean[] objs) {
    int size = objs.length;
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size; i++)
      buff.append("\nItem[").append(i).append("]\n").append(objs[i]);
    return buff.toString();
  }

  public static boolean isEmpty(String val) {
    if (val == null || val.length() == 0)
      return true;
    return false;
  }

  public static String stringFormat(String pattern, Object[] arguments) {
    return MessageFormat.format(pattern, arguments);
  }

  public static String stringFormat(String pattern) {
    return pattern;
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3) {
    Object[] argList = { arg1, arg2, arg3 };
    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5 };
    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5,
      Object arg6) {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6 };
    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5,
      Object arg6, Object arg7) {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6, arg7 };
    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5,
      Object arg6, Object arg7, Object arg8) {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8 };
    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5,
      Object arg6, Object arg7, Object arg8, Object arg9) {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9 };
    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5,
      Object arg6, Object arg7, Object arg8, Object arg9, Object arg10) {
    Object[] argList = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10 };
    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5,
      Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11) {
    Object[] argList = {
        arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10,
        arg11 };
    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5,
      Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
    Object[] argList = {
        arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10,
        arg11, arg12 };
    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5,
      Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13) {
    Object[] argList = {
        arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10,
        arg11, arg12, arg13 };
    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5,
      Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13,
      Object arg14) {
    Object[] argList = {
        arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10,
        arg11, arg12, arg13, arg14 };
    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5,
      Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13,
      Object arg14, Object arg15) {
    Object[] argList = {
        arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10,
        arg11, arg12, arg13, arg14, arg15 };
    return MessageFormat.format(pattern, argList);
  }

  public static String toString(int val) {
    return Integer.toString(val);
  }

  public static String toCommaString(Long[] longArray) {
    if (longArray == null || longArray.length <= 0)
      return "";
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < longArray.length; i++) {
      buff.append(longArray[i]);
      if (i != longArray.length - 1)
        buff.append(",");
    }
    return buff.toString();
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2) {
    Object[] argList = { arg1, arg2 };
    return MessageFormat.format(pattern, argList);
  }

  public static boolean isNotEmpty(String val) {
    if (val == null || val.length() == 0)
      return false;
    return true;
  }

  public static String stringFormat(String pattern, Object arg1) {
    Object[] argList = { arg1 };
    return MessageFormat.format(pattern, argList);
  }

  public static String stringFormat(String pattern, Object arg1, Object arg2, Object arg3, Object arg4) {
    Object[] argList = { arg1, arg2, arg3, arg4 };
    return MessageFormat.format(pattern, argList);
  }

  public static Map<String, String> splitPara(String paraSrc, String sepa) {
    if (paraSrc == null || paraSrc.trim().length() == 0)
      return null;
    LinkedHashMap<String, String> paraMap = new LinkedHashMap<>();
    if (sepa == null || "".equals(sepa))
      sepa = ",";
    String[] paras = paraSrc.split(sepa);
    String[] tmpResult = null;
    for (int i = 0, j = 0; i < paras.length; i++) {
      tmpResult = paras[i].split("=");
      if (tmpResult.length >= 2) {
        paraMap.put(tmpResult[0].trim(), tmpResult[1]);
      } else if (tmpResult.length == 1) {
        if (paras[i].indexOf("=") >= 0) {
          paraMap.put(tmpResult[0].trim(), "");
        } else {
          paraMap.put("TEXT." + j, paras[i]);
          j++;
        }
      }
    }
    return paraMap;
  }

  public static String[] findAll(String src, String pattern) {
    if (src == null)
      return new String[0];
    if (pattern == null)
      return new String[0];
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(src);
    Collection<String> l = new ArrayList<>();
    while (m.find())
      l.add(m.group());
    return l.<String>toArray(new String[0]);
  }

  public static boolean isNumeric(String str) {
    if (str == null)
      return false;
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isDigit(str.charAt(i)))
        return false;
    }
    return true;
  }

  public static String getStringDivideByCommaFromList(ArrayList array) {
    if (array == null || array.isEmpty())
      return "";
    Object[] bundle = array.toArray(new Object[array.size()]);
    String ret = null;
    if (bundle[0] != null)
      ret = bundle[0].toString();
    StringBuilder sb = new StringBuilder(ret);
    for (int i = 1; i < bundle.length; i++)
      sb.append(",").append(bundle[i]);
    return sb.toString();
  }

}
