package com.sts.sinorita.util;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.ParamNode;
import com.sts.sinorita.helper.BillingHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public final class DataFormatter {
  static Logger logger = LoggerFactory.getLogger(DataFormatter.class);

  public static String formatNumber(String template, String num) {
    ParamNode paramNode = ParamNode.parseParamExp(template);
    return formatNumber(paramNode, num, null);
  }

  private static String formatNumber(ParamNode paramNode, String num, Map<String, String> map) {
    try {
      String defaultNum = "";
      if (num.isEmpty())
        if (paramNode.getAttrMap().containsKey("M")) {
          defaultNum = (String) paramNode.getAttrMap().get("M");
          if (!paramNode.getAttrMap().containsKey("P"))
            return defaultNum;
          num = defaultNum;
        } else {
          num = "0";
        }
      num = num.replaceAll(",", "");
      StringBuffer s = new StringBuffer();
      boolean isLong = !num.contains(".");
      Double doubleNum = Double.valueOf(num);
      if (paramNode.getAttrMap().containsKey("P")) {
        String formula = instanceFormula(paramNode.getName(), doubleNum.toString(), paramNode
            .getAttrMap().get("P").toString(), map);
        String formulaNum = Eval.calculate(formula);
        if (!formulaNum.isEmpty()) {
          doubleNum = Double.valueOf(formulaNum);
          isLong = (doubleNum.longValue() == doubleNum.doubleValue());
        }
      }
      if (paramNode.getAttrMap().get("R") != null && "1".equals(paramNode.getAttrMap().get("R").toString()) && paramNode
          .getAttrMap().get("D") != null && !"".equals(paramNode.getAttrMap().get("D").toString())) {
        BigDecimal bd = new BigDecimal(String.valueOf(doubleNum));
        int i = Integer.valueOf(paramNode.getAttrMap().get("D").toString()).intValue();
        bd = bd.setScale(i, RoundingMode.HALF_UP);
        doubleNum = Double.valueOf(bd.doubleValue());
      }
      DecimalFormat df = new DecimalFormat("0.0000000000000");
      String strNum = df.format(doubleNum);
      List back = dealStrNum(strNum, paramNode, isLong, s, doubleNum);
      s = (StringBuffer) back.get(0);
      doubleNum = (Double) back.get(1);
      DecimalFormat df1 = new DecimalFormat(s.toString());
      String newStrNum = df1.format(doubleNum);
      if (map != null && !map.containsKey(paramNode.getName())) {
        logger.debug("Add parameter defined by template to value map:[{}={}]",
            new Object[] { paramNode.getName(), newStrNum });
        map.put(paramNode.getName(), newStrNum);
      }
      return newStrNum;
    } catch (Exception e) {
      // throw new BaseAppException("S-CNT-01020", null, 0, e, num, null, null);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-CNT-01020"));

    }
  }

  private static String instanceFormula(String paramName, String paramValue, String formula, Map map) {
    if (map != null && !map.isEmpty()) {
      String temp = formula;
      int iBegin = -1;
      int iEnd = -1;
      int level = -1;
      for (int i = 0; i < formula.length(); i++) {
        if ('{' == formula.charAt(i)) {
          if (level == -1)
            iBegin = i;
          level++;
        } else if ('}' == formula.charAt(i)) {
          if (level == 0) {
            iEnd = i;
            String tempParamName = formula.substring(iBegin + 1, iEnd);
            ParamNode paramNode = ParamNode.parseParamExp(tempParamName);
            String value = formatNumber(paramNode, (String) map.get(paramNode.getName()), map);
            temp = StringUtil.replace(temp, "{" + tempParamName + "}", value);
            iBegin = -1;
            iEnd = -1;
            level = -1;
          } else if (level > 0) {
            level--;
          }
        }
      }
      formula = temp;
    } else {
      formula = StringUtil.replace(formula, "{" + paramName + "}", paramValue);
    }
    formula = StringUtil.replace(formula, paramName, paramValue);
    return formula;
  }

  public static String formatDate(String template, String src) {
    ParamNode paramNode = ParamNode.parseParamExp(template);
    return formatDate(paramNode, src);
  }

  private static String formatDate(ParamNode paramNode, String src) {
    if (src.isEmpty() && paramNode.getAttrMap().containsKey("M"))
      return (String) paramNode.getAttrMap().get("M");
    if (src.isEmpty())
      return "";
    long seconds = 0L;
    if (paramNode.getAttrMap().get("P") != null) {
      String strSeconds = paramNode.getAttrMap().get("P").toString().trim();
      seconds = Long.parseLong(strSeconds);
    }
    String format = "yyyy-MM-dd HH:mm:ss";
    if (paramNode.getAttrMap().get("F") != null)
      format = paramNode.getAttrMap().get("F").toString().trim();
    return checkDateTime(src, format, seconds);
  }

  private static String checkDateTime(String src, String format, long seconds) {
    try {
      Date date = null;
      SimpleDateFormat dateFormat = null;
      if (src.length() == 19) {
        if (src.indexOf('-') > 0) {
          dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          date = dateFormat.parse(src);
        } else if (src.indexOf('/') > 0) {
          dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
          date = dateFormat.parse(src);
        }
      } else if (src.length() == 14) {
        dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        date = dateFormat.parse(src);
      } else if (src.length() == 8 && src.indexOf(':') > 0) {
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        date = dateFormat.parse(src);
      } else if (src.indexOf('-') > 0) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.parse(src);
      } else if (src.indexOf('/') > 0) {
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        date = dateFormat.parse(src);
      } else if (src.length() == 8) {
        dateFormat = new SimpleDateFormat("yyyyMMdd");
        date = dateFormat.parse(src);
      } else {
        dateFormat = new SimpleDateFormat("yyyy");
        date = dateFormat.parse(src);
      }
      if (null == date)
        throw new Exception("S-RES-70007");
      long time = date.getTime() + seconds * 1000L;
      date = new Date(time);
      SimpleDateFormat destFormat = selectMatchedDateFormat(format);
      return destFormat.format(date);
    } catch (Exception e) {
      // throw new BaseAppException("S-CNT-01015", null, 0, e, src, null, null);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-CNT-01015"));
    }
  }

  private static SimpleDateFormat selectMatchedDateFormat(String format) {
    if (null == format)
      format = "yyyy-MM-dd HH:mm:ss";
    if (format.equals("yyyy-MM-dd HH:mm:ss") || format.equals("yyyy-MM-dd-HH:mm:ss"))
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if (format.equals("yyyy-MM-dd"))
      return new SimpleDateFormat("yyyy-MM-dd");
    if (format.equals("yyyyMMddHHmmss"))
      return new SimpleDateFormat("yyyyMMddHHmmss");
    if (format.equals("yyyyMMdd"))
      return new SimpleDateFormat("yyyyMMdd");
    if (format.equals("yyyy"))
      return new SimpleDateFormat("yyyy");
    if (format.equals("yyyy/MM/dd"))
      return new SimpleDateFormat("yyyy/MM/dd");
    if (format.equals("yyyy/MM/dd HH:mm:ss"))
      return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    if (format.equals("dd/MM/yyyy"))
      return new SimpleDateFormat("dd/MM/yyyy");
    if (format.equals("ddMMyyyy"))
      return new SimpleDateFormat("ddMMyyyy");
    return new SimpleDateFormat(format);
  }

  private static List dealStrNum(String strNum, ParamNode paramNode, boolean isLong, StringBuffer s, Double doubleNum) {
    List<Object> returnList = new ArrayList();
    StringBuffer returnS = s;
    Double returnD = doubleNum;
    if (paramNode.getAttrMap().get("K") != null && "1".equals(paramNode.getAttrMap().get("K").toString())) {
      returnS.append("#,##0");
    } else {
      returnS.append("###0");
    }
    for (int i = strNum.length() - 1; i >= strNum.indexOf('.'); i--) {
      if ('0' != strNum.charAt(i)) {
        if ('.' == strNum.charAt(i)) {
          strNum = strNum.substring(0, i);
          break;
        }
        strNum = strNum.substring(0, i + 1);
        break;
      }
    }
    int precision = 0;
    if (paramNode.getAttrMap().get("D") != null && !"".equals(paramNode.getAttrMap().get("D").toString())) {
      precision = Integer.valueOf(paramNode.getAttrMap().get("D").toString()).intValue();
    } else if (!isLong &&
        strNum.indexOf(".") >= 0) {
      precision = strNum.length() - strNum.indexOf(".") - 1;
    }
    if (precision > 0) {
      if (strNum.indexOf(".") >= 0) {
        strNum = strNum + "00000000000000000000";
      } else {
        strNum = strNum + ".00000000000000000000";
      }
      strNum = strNum.substring(0, strNum.indexOf(".") + 1 + precision);
      returnD = Double.valueOf(strNum);
      returnS.append(".");
      while (precision > 0) {
        returnS.append("0");
        precision--;
      }
    }
    returnList.add(0, returnS);
    returnList.add(1, returnD);
    return returnList;
  }
}
