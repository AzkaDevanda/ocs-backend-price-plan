package com.sts.sinorita.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StringParser {

  private static final int MODE_STRING = 1;

  public static List<String> adjustValueStrToSqlParam(String line, String valueDelimiter) {
    if (line == null || line.isBlank()) {
      return Collections.emptyList();
    }

    // kalau sudah dikasih format 'CODE1','CODE2', hapus tanda kutip
    if (line.startsWith("'") && line.endsWith("'")) {
      line = line.replace("'", "");
    }

    // delimiter default adalah koma
    valueDelimiter = checkDelimiter(valueDelimiter, ",", 0);

    // split string berdasarkan delimiter
    return Arrays.stream(line.split(valueDelimiter))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .collect(Collectors.toList());
  }

  private static String checkDelimiter(String delimiter, String defaultValue, int mode) {
    delimiter = checkDelimiter(delimiter, defaultValue);
    if (mode == 0)
      return stringToRegex(delimiter);
    if (mode == 1)
      return regexToString(delimiter);
    return delimiter;
  }

  private static String stringToRegex(String delimiter) {
    if ("|".equals(delimiter))
      return "\\|";
    return delimiter;
  }

  private static String regexToString(String delimiter) {
    if ("\\|".equals(delimiter))
      return "|";
    return delimiter;
  }

  private static String checkDelimiter(String delimiter, String defaultValue) {
    if (delimiter == null || delimiter.isEmpty())
      return defaultValue;
    return delimiter;
  }

  public static String adjustValueStrToSqlParamSingleValue(String line, String valueDelimiter) {
    if (StringUtil.isEmpty(line))
      return null;
    if (line.startsWith("'") && line.endsWith("'"))
      return line;
    valueDelimiter = checkDelimiter(valueDelimiter, ",", 0);
    return "'" + line.replaceAll(valueDelimiter, "','") + "'";
  }


    public static boolean checkValueInValueStr(
            String line, String value, String valueDelimiter) {

        if (StringUtil.isEmpty(line) || StringUtil.isEmpty(value)) {
            return false;
        }

        valueDelimiter = checkDelimiter(valueDelimiter, ",", MODE_STRING);
        String tempLine = valueDelimiter + line + valueDelimiter;
        String tempValue = valueDelimiter + value + valueDelimiter;
        return tempLine.contains(tempValue);
    }
}
