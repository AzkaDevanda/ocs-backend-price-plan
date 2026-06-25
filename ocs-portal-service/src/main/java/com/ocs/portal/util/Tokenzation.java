package com.ocs.portal.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocs.portal.dto.request.balanceAdjustment.Function;
import com.ocs.portal.dto.request.balanceAdjustment.Operator;
import com.ocs.portal.dto.request.balanceAdjustment.Token;
import com.ocs.portal.dto.request.balanceAdjustment.Variable;

public class Tokenzation {
  static Logger logger = LoggerFactory.getLogger(Tokenzation.class);

  public static int binaryNumber(String s) {
    int duzina = 2;
    int totalLength = s.length();
    if (totalLength < 3)
      return 0;
    if ("#b".equals(s.substring(0, 2)) || "#B".equals(s.substring(0, 2)))
      while (duzina < totalLength && isBinaryChar(s.charAt(duzina)))
        duzina++;
    if (duzina == 2)
      return 0;
    return duzina;
  }

  public static int octalNumber(String s) {
    int duzina = 2;
    int totalLength = s.length();
    if (totalLength < 3)
      return 0;
    if ("#o".equals(s.substring(0, 2)) || "#O".equals(s.substring(0, 2)))
      while (duzina < totalLength && isOctalChar(s.charAt(duzina)))
        duzina++;
    if (duzina == 2)
      return 0;
    return duzina;
  }

  public static int hexadecimalNumber(String s) {
    int duzina = 2;
    int totalLength = s.length();
    if (totalLength < 3)
      return 0;
    if ("#h".equals(s.substring(0, 2)) || "#H".equals(s.substring(0, 2)))
      while (duzina < totalLength && isHexadecimalChar(s.charAt(duzina)))
        duzina++;
    if (duzina == 2)
      return 0;
    return duzina;
  }

  public static boolean isHexadecimalChar(char c) {
    if (Character.isDigit(c))
      return true;
    if ('A' <= upper(c) && upper(c) <= 'F')
      return true;
    return false;
  }

  public static boolean isOctalChar(char c) {
    return ('0' <= c && c <= '7');
  }

  public static boolean isBinaryChar(char c) {
    return (c == '0' || c == '1');
  }

  private static boolean isDecimal(String s) {
    if (!s.isEmpty()) {
      if (Character.isDigit(s.charAt(0)))
        return true;
      Pattern digit = Pattern.compile("[E.+-][0-9]");
      Matcher isDigit = digit.matcher(s);
      if (isDigit.matches())
        return true;
    }
    return false;
  }

  public static int decimalNumber(String expression) {
    int duzina = 0;
    int j;
    for (j = 0; j < expression.length(); j++) {
      String subStr = null;
      if (j == expression.length() - 1) {
        subStr = expression.substring(j, j + 1);
      } else {
        subStr = expression.substring(j, j + 2);
      }
      if (!isDecimal(subStr))
        break;
    }
    for (int i = j; i > 0; i--) {
      String podizraz = expression.substring(0, i);
      int subexpressionLength = podizraz.length();
      try {
        stringToDouble(podizraz);
        char lastCharacter = podizraz.charAt(subexpressionLength - 1);
        if (upper(lastCharacter) == 'F' || upper(lastCharacter) == 'D') {
          duzina = subexpressionLength - 1;
          break;
        }
        duzina = subexpressionLength;
        break;
      } catch (NumberFormatException e) {
        logger.warn("", e);
      }
    }
    return duzina;
  }

  private static char upper(char znak) {
    return Character.toUpperCase(znak);
  }

  public static double stringToDouble(String s) {
    return Double.valueOf(s).doubleValue();
  }

  public static Variable variable(String expression, List<Variable> variable) {
    for (int i = 0; i < variable.size(); i++) {
      Variable v = variable.get(i);
      int variableLength = v.variableP.length();
      if (variableLength <= expression.length())
        if (expression.substring(0, variableLength).equals(v.variableP))
          return v;
    }
    return null;
  }

  public static Function funtion(String expression, List<Function> functions) {
    for (int i = 0; i < functions.size(); i++) {
      Function f = functions.get(i);
      int functionLength = f.functionP.length();
      if (functionLength <= expression.length())
        if (expression.substring(0, functionLength).equals(f.functionP))
          return f;
    }
    return null;
  }

  public static Operator operator(String expression, List<Operator> operators) {
    for (int i = 0; i < operators.size(); i++) {
      Operator o = operators.get(i);
      int duzina_operatora = o.operatorStr.length();
      if (duzina_operatora <= expression.length())
        if (expression.substring(0, duzina_operatora).equals(o.operatorStr))
          return o;
    }
    return null;
  }

  public static List<Token> rastavljanjeIzraza(String s, List<Variable> variable, List<Operator> operator,
      List<Function> function) {
    List<Token> token = new ArrayList<>();
    boolean pennant = false;
    int position = 0;
    while (!"".equals(s)) {
      position += s.length() - s.trim().length();
      s = s.trim();
      int duzina = hexadecimalNumber(s);
      if (duzina > 0) {
        String dio = s.substring(0, duzina);
        token.add(new Token(dio, 'H', position, duzina));
        s = s.substring(duzina);
        pennant = true;
        position += duzina;
        continue;
      }
      duzina = octalNumber(s);
      if (duzina > 0) {
        String dio = s.substring(0, duzina);
        token.add(new Token(dio, 'O', position, duzina));
        s = s.substring(duzina);
        pennant = true;
        position += duzina;
        continue;
      }
      duzina = binaryNumber(s);
      if (duzina > 0) {
        String dio = s.substring(0, duzina);
        token.add(new Token(dio, 'B', position, duzina));
        s = s.substring(duzina);
        pennant = true;
        position += duzina;
        continue;
      }
      duzina = decimalNumber(s);
      if ((duzina > 0 && !pennant) || (duzina > 0 && s.charAt(0) != '-' && s.charAt(0) != '+')) {
        String dio = s.substring(0, duzina);
        Double broj = Double.valueOf(stringToDouble(dio));
        token.add(new Token(broj, 'D', position, duzina));
        s = s.substring(duzina);
        pennant = true;
        position += duzina;
        continue;
      }
      Variable v = variable(s, variable);
      if (v != null) {
        duzina = v.variableP.length();
        token.add(new Token(v, 'V', position, duzina));
        s = s.substring(duzina);
        pennant = true;
        position += duzina;
        continue;
      }
      Function f = funtion(s, function);
      if (f != null) {
        duzina = f.functionP.length();
        token.add(new Token(f, 'F', position, duzina));
        s = s.substring(duzina);
        pennant = true;
        position += duzina;
        continue;
      }
      Operator o = operator(s, operator);
      if (o != null) {
        duzina = o.operatorStr.length();
        token.add(new Token(o, 'P', position, duzina));
        s = s.substring(duzina);
        pennant = false;
        position += duzina;
        continue;
      }
      boolean status = ParenthesesCheck.isOpenParentheses(s.charAt(0));
      if (status) {
        String dio = s.substring(0, 1);
        token.add(new Token(dio, '(', position, 1));
        s = s.substring(1);
        pennant = false;
        position++;
        continue;
      }
      status = ParenthesesCheck.isClosedParentheses(s.charAt(0));
      if (status) {
        String dio = s.substring(0, 1);
        token.add(new Token(dio, ')', position, 1));
        s = s.substring(1);
        pennant = true;
        position++;
        continue;
      }
      if (s.charAt(0) == ',') {
        String dio = ",";
        token.add(new Token(dio, 'Z', position, 1));
        s = s.substring(1);
        pennant = false;
        position++;
        continue;
      }
      token.add(new Token(s.substring(0, 1), 'G', position, 1));
      pennant = false;
      s = s.substring(1);
      position++;
    }
    return token;
  }

}
