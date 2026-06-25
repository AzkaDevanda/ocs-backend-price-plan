package com.ocs.portal.util;

import java.util.ArrayList;
import java.util.List;

import com.ocs.portal.dto.request.balanceAdjustment.Function;
import com.ocs.portal.dto.request.balanceAdjustment.Span;
import com.ocs.portal.dto.request.balanceAdjustment.Token;

public class ExpressionCheck {
  static final String[] ALLOWED_COMBINATIONS = new String[] {
      "DP", "D)", "DZ", "BP", "B)", "BZ", "HP", "H)", "HZ", "OP",
      "O)", "OZ", "VP", "V)", "VZ", "F(", "PD", "PB", "PH", "PO",
      "PV", "PF", "P(", "(D", "(B", "(H", "(O", "(V", "(F", "((",
      ")P", "))", ")Z", "ZD", "ZB", "ZH", "ZO", "ZV", "ZF", "Z(" };

  public static boolean starting(List<Token> tokens, Span errorRange, StringBuffer errorText) {
    boolean expressionGood = validityCheck(tokens, errorRange, errorText);
    if (!expressionGood)
      return false;
    expressionGood = checkStartOfExpression(tokens, errorRange, errorText);
    if (!expressionGood)
      return false;
    expressionGood = EndOfExpressionCheck(tokens, errorRange, errorText);
    if (!expressionGood)
      return false;
    expressionGood = orderCheck(tokens, errorRange, errorText);
    if (!expressionGood)
      return false;
    expressionGood = checkFunction(tokens, errorRange, errorText);
    if (!expressionGood)
      return false;
    expressionGood = checkComma(tokens, errorRange, errorText);
    if (!expressionGood)
      return false;
    return true;
  }

  private static boolean commaGood(List<Token> tokens, int poz) {
    int i = 0;
    while (poz >= 0) {
      Token token = tokens.get(poz);
      if (token.mark == '(') {
        if (i == 0)
          break;
        i--;
      } else if (token.mark == ')') {
        i++;
      }
      poz--;
    }
    if (poz <= 0)
      return false;
    Token t = tokens.get(poz - 1);
    if (t.mark != 'F')
      return false;
    return true;
  }

  private static boolean checkComma(List<Token> tokens, Span errorRange, StringBuffer errorText) {
    boolean povrat = true;
    List<Token> auxiliaryList = new ArrayList<>(tokens);
    Calculation.izbacivanjeVisestrukihZagrada(auxiliaryList);
    for (int i = 0; i < auxiliaryList.size(); i++) {
      Token t = auxiliaryList.get(i);
      if (t.mark == 'Z' && !commaGood(auxiliaryList, i)) {
        errorRange.beginning = t.position;
        errorRange.end = t.position + 1;
        errorText.append("comma is used only for separating arguments of functions");
        povrat = false;
        break;
      }
    }
    return povrat;
  }

  public static int endFunction(List<Token> tokenList, int functionPosition) {
    int i = functionPosition + 2;
    int bracketCounter = 1;
    while (bracketCounter != 0) {
      Token t = tokenList.get(i);
      if (t.mark == '(') {
        bracketCounter++;
      } else if (t.mark == ')') {
        bracketCounter--;
      }
      i++;
    }
    return i - 1;
  }

  private static int numberOfArgumentsFunction(List<Token> tokenList, int functionPosition) {
    int i = functionPosition + 2;
    int numberOfCommas = 0;
    while (i < endFunction(tokenList, functionPosition)) {
      Token t = tokenList.get(i);
      if (t.mark == 'F') {
        i = endFunction(tokenList, i);
      } else if (t.mark == 'Z') {
        numberOfCommas++;
      }
      i++;
    }
    return numberOfCommas + 1;
  }

  private static boolean checkFunction(List<Token> tokens, Span errorRange, StringBuffer errorText) {
    boolean povrat = true;
    for (int i = 0; i < tokens.size(); i++) {
      Token t = tokens.get(i);
      if (t.mark == 'F') {
        Function fn = (Function) t.tokenP;
        if (fn.numberOfArguments != 0 && numberOfArgumentsFunction(tokens, i) != fn.numberOfArguments) {
          errorRange.beginning = t.position;
          errorRange.end = t.position + t.length;
          errorText.append("function \"" + fn + "\"" + " has " + fn.numberOfArguments + " argument");
          if (fn.numberOfArguments != 1)
            errorText.append('s');
          povrat = false;
          break;
        }
      }
    }
    return povrat;
  }

  private static boolean redoslijedLos(String compound) {
    boolean povrat = true;
    for (int i = 0; i < ALLOWED_COMBINATIONS.length; i++) {
      if (ALLOWED_COMBINATIONS[i].equals(compound)) {
        povrat = false;
        break;
      }
    }
    return povrat;
  }

  private static boolean orderCheck(List<Token> tokens, Span errorRange, StringBuffer errorText) {
    boolean povrat = true;
    StringBuffer compound = new StringBuffer("  ");
    for (int i = 0; i < tokens.size() - 1; i++) {
      Token t1 = tokens.get(i);
      Token t2 = tokens.get(i + 1);
      compound.setCharAt(0, t1.mark);
      compound.setCharAt(1, t2.mark);
      if (redoslijedLos(compound.toString())) {
        errorRange.beginning = t2.position;
        errorRange.end = t2.position + t2.length;
        errorText.append(illegalCombination(compound.toString()));
        povrat = false;
        break;
      }
    }
    return povrat;
  }

  private static String illegalCombination(String combination) {
    char mark1 = combination.charAt(0);
    char mark2 = combination.charAt(1);
    return nazivTokena(mark2) + " " + "after" + " " + nazivTokena(mark1);
  }

  public static String nazivTokena(char mark) {
    switch (mark) {
      case 'D':
        return "number";
      case 'B':
        return "binary number";
      case 'H':
        return "hexadecimal number";
      case 'O':
        return "octal number";
      case 'V':
        return "variable";
      case 'F':
        return "function";
      case 'P':
        return "operator";
      case '(':
        return "open parenthesis";
      case ')':
        return "closed parenthesis";
      case 'Z':
        return "comma";
    }
    return "error";
  }

  private static boolean checkStartOfExpression(List<Token> tokens, Span errorRange, StringBuffer errorText) {
    Token t = tokens.get(0);
    char mark = t.mark;
    if (mark == 'P') {
      errorText.append("expression must not begin with operator");
    } else if (mark == ')') {
      errorText.append("expression must not begin with closed parenthesis");
    } else if (mark == 'Z') {
      errorText.append("expression must not begin with comma");
    } else {
      return true;
    }
    errorRange.beginning = 0;
    errorRange.end = t.length;
    return false;
  }

  private static boolean EndOfExpressionCheck(List<Token> tokens, Span errorRange, StringBuffer errorText) {
    Token t = tokens.get(tokens.size() - 1);
    char mark = t.mark;
    if (mark == 'P') {
      errorText.append("expression must not end with operator");
    } else if (mark == 'F') {
      errorText.append("expression must not end with function");
    } else if (mark == '(') {
      errorText.append("expression must not end with open parenthesis");
    } else if (mark == 'Z') {
      errorText.append("expression must not end with comma");
    } else {
      return true;
    }
    errorRange.beginning = t.position;
    errorRange.end = t.position + t.length;
    return false;
  }

  private static boolean validityCheck(List<Token> tokens, Span errorRange, StringBuffer errorText) {
    boolean error = false;
    int beginning;
    for (beginning = 0; beginning < tokens.size(); beginning++) {
      Token t = tokens.get(beginning);
      if (t.mark == 'G') {
        error = true;
        break;
      }
    }
    if (error) {
      int kraj;
      for (kraj = beginning + 1; kraj < tokens.size() && ((Token) tokens.get(kraj)).mark == 'G'; kraj++)
        ;
      Token t = tokens.get(beginning);
      beginning = t.position;
      t = tokens.get(kraj - 1);
      kraj = t.position;
      errorRange.beginning = beginning;
      errorRange.end = kraj + 1;
      errorText.append("unknown part of expression");
    }
    return !error;
  }
}
