package com.sts.sinorita.helper;

import org.springframework.stereotype.Service;

@Service
public class AccountHelper {

  public static boolean isInCommaText(String commaText, String subtext) {
    if (commaText == null || subtext == null)
      return false; 
    int len = subtext.length();
    if (len > commaText.length())
      return false; 
    int index = commaText.indexOf(subtext, 0);
    while (index >= 0) {
      if ((index == 0 || commaText.charAt(index - 1) == ',') && (
        index + len >= commaText.length() || commaText.charAt(index + len) == ','))
        return true; 
      if (index + len >= commaText.length())
        break; 
      index = commaText.indexOf(subtext, index + len + 1);
    } 
    return false;
  }

}
