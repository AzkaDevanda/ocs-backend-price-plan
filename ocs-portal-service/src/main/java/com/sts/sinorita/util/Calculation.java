package com.sts.sinorita.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.sts.sinorita.dto.request.balanceAdjustment.Function;
import com.sts.sinorita.dto.request.balanceAdjustment.Operator;
import com.sts.sinorita.dto.request.balanceAdjustment.Span;
import com.sts.sinorita.dto.request.balanceAdjustment.Token;
import com.sts.sinorita.dto.request.balanceAdjustment.Variable;

public final class Calculation {
  public static int positionOfTheFirstClosedParenthesis(List<Token> tokeni) {
    for (int i = 0; i < tokeni.size(); i++) {
      Token t = tokeni.get(i);
      if (t.mark == ')')
        return i;
    }
    return 0;
  }

  public static int positionOpenParentheses(List<Token> tokeni, int closedBracketPosition) {
    int i = closedBracketPosition - 2;
    while (i >= 0) {
      Token t = tokeni.get(i);
      if (t.mark == '(')
        return i;
      i--;
    }
    return 0;
  }

  public static Span rangeParenthesis(List<Token> tokeni) {
    Span span = null;
    int posClosingBrackets = positionOfTheFirstClosedParenthesis(tokeni);
    if (posClosingBrackets != 0) {
      int posOpenBracket = positionOpenParentheses(tokeni, posClosingBrackets);
      span = new Span(posOpenBracket, posClosingBrackets);
    }
    return span;
  }

  public static String connectionOfTokens(List<Token> tokeni) {
    String expression = "";
    for (int i = 0; i < tokeni.size(); i++) {
      Token t = tokeni.get(i);
      if (t.mark == 'D') {
        double broj = ((Double) t.tokenP).doubleValue();
        expression = expression + formattedNumber(broj);
      } else {
        expression = expression + t.tokenP;
      }
      if (expression.endsWith(".0"))
        expression = expression.substring(0, expression.length() - 2);
      expression = expression + " ";
    }
    return expression;
  }

  public static String formattedNumber(double d) {
    if (1.0E-15D < Math.abs(d) && Math.abs(d) < 1.0E15D) {
      DecimalFormat df = new DecimalFormat("#.###############################");
      return df.format(d).replace(',', '.');
    }
    return String.valueOf(d);
  }

  public static int positionOfTheOperator(List<Token> tokeni, Span bracketRange) {
    byte max_priority = Byte.MAX_VALUE;
    int max_position = 0;
    for (int i = bracketRange.beginning + 2; i <= bracketRange.end - 2; i++) {
      Token t = tokeni.get(i);
      if (t.mark == 'P') {
        byte priority = ((Operator) t.tokenP).priority;
        String operator = ((Operator) t.tokenP).operatorStr;
        if (priority < max_priority
            || (("^".equals(operator) || "**".equals(operator)) && priority == max_priority)) {
          max_priority = priority;
          max_position = i;
        }
      }
    }
    return max_position;
  }

  public static double resultOperation(double broj1, double broj2, String op) {
    double result = 0.0D;
    if ("^".equals(op) || "**".equals(op)) {
      result = Matematika.potenciranje(broj1, broj2);
    } else if ("*".equals(op)) {
      result = Matematika.mnozenje(broj1, broj2);
    } else if ("/".equals(op)) {
      result = Matematika.dijeljenje(broj1, broj2);
    } else if ("mod".equals(op)) {
      result = Math.IEEEremainder(broj1, broj2);
    } else if ("posmod".equals(op)) {
      result = broj1 % broj2;
    } else if ("%".equals(op)) {
      result = broj1 % broj2;
    } else if ("+".equals(op)) {
      result = Matematika.zbrajanje(broj1, broj2);
    } else if ("-".equals(op)) {
      result = Matematika.oduzimanje(broj1, broj2);
    } else if ("<<".equals(op)) {
      result = ((long) broj1 << (int) (long) broj2);
    } else if (">>".equals(op)) {
      result = ((long) broj1 >> (int) (long) broj2);
    } else if (">>>".equals(op)) {
      result = ((long) broj1 >>> (int) (long) broj2);
    } else if ("<".equals(op)) {
      if (broj1 < broj2) {
        result = 1.0D;
      } else {
        result = 0.0D;
      }
    } else if (">".equals(op)) {
      if (broj1 > broj2) {
        result = 1.0D;
      } else {
        result = 0.0D;
      }
    } else if ("<=".equals(op)) {
      if (broj1 <= broj2) {
        result = 1.0D;
      } else {
        result = 0.0D;
      }
    } else if (">=".equals(op)) {
      if (broj1 >= broj2) {
        result = 1.0D;
      } else {
        result = 0.0D;
      }
    } else if ("==".equals(op)) {
      if (broj1 == broj2) {
        result = 1.0D;
      } else {
        result = 0.0D;
      }
    } else if ("!=".equals(op) || "<>".equals(op)) {
      if (broj1 != broj2) {
        result = 1.0D;
      } else {
        result = 0.0D;
      }
    } else if ("and".equals(op)) {
      result = ((long) broj1 & (long) broj2);
    } else if ("xor".equals(op)) {
      result = ((long) broj1 ^ (long) broj2);
    } else if ("or".equals(op)) {
      result = ((long) broj1 | (long) broj2);
    } else if ("&&".equals(op)) {
      if (broj1 != 0.0D && broj2 != 0.0D) {
        result = 1.0D;
      } else {
        result = 0.0D;
      }
    } else if ("||".equals(op)) {
      if (broj1 != 0.0D || broj2 != 0.0D) {
        result = 1.0D;
      } else {
        result = 0.0D;
      }
    }
    return result;
  }

  public static double valueOperand(Token t) {
    if (t.mark == 'V')
      return ((Variable) t.tokenP).value;
    if (t.mark == 'D')
      return ((Double) t.tokenP).doubleValue();
    if (t.mark == 'H')
      return Matematika.bazaBroj(((String) t.tokenP).substring(2), 16);
    if (t.mark == 'O')
      return Matematika.bazaBroj(((String) t.tokenP).substring(2), 8);
    if (t.mark == 'B')
      return Matematika.bazaBroj(((String) t.tokenP).substring(2), 2);
    return 0.0D;
  }

  public static boolean calculationStep(List<Token> tokeni) {
    boolean has_braces = true;
    Span rang = rangeParenthes(tokeni);
    if (rang == null) {
      rang = new Span(-1, tokeni.size());
      has_braces = false;
    }
    int poz_max_op = operationPosition(tokeni, rang);
    if (poz_max_op == 0) {
      if (!has_braces)
        return false;
      double d = resultFunction(tokeni, rang.beginning - 1);
      ejectToken(tokeni, rang.beginning - 1);
      Token token = new Token(Double.valueOf(d), 'D', 0, 0);
      tokeni.set(rang.beginning - 1, token);
      throwOutParentheses(tokeni, rang.beginning - 1);
      return true;
    }
    Token t = tokeni.get(poz_max_op - 1);
    double operand1 = valueOperand(t);
    t = tokeni.get(poz_max_op + 1);
    double operand2 = valueOperand(t);
    t = tokeni.get(poz_max_op);
    String op = ((Operator) t.tokenP).operatorStr;
    double result = resultOperation(operand1, operand2, op);
    tokeni.remove(poz_max_op + 1);
    tokeni.remove(poz_max_op);
    t = new Token(Double.valueOf(result), 'D', 0, 0);
    tokeni.set(poz_max_op - 1, t);
    throwOutParentheses(tokeni, poz_max_op - 1);
    return true;
  }

  private static void ejectToken(List<Token> tokeni, int function_position) {
    while (((Token) tokeni.get(function_position)).mark != ')')
      tokeni.remove(function_position);
  }

  public static void multipleParenthesesEjection(List<Token> tokeni) {
    List<Token> helpful = new ArrayList<>(tokeni);
    Span r;
    while ((r = rangeParenthes(helpful)) != null) {
      if (r.beginning != 0 && r.end != helpful.size() - 1 && ((Token) helpful.get(r.beginning - 1)).mark == '('
          && ((Token) helpful
              .get(r.end + 1)).mark == ')') {
        helpful.remove(r.end + 1);
        helpful.remove(r.beginning - 1);
        tokeni.remove(r.end + 1);
        tokeni.remove(r.beginning - 1);
        continue;
      }
      if (r.beginning == 0 && r.end == tokeni.size() - 1) {
        tokeni.remove(r.end);
        tokeni.remove(r.beginning);
        continue;
      }
      helpful.set(r.end, new Token(null, false, 0, 0));
      helpful.set(r.beginning, new Token(null, false, 0, 0));
    }
  }

  private static void throwOutParentheses(List<Token> tokeni, int poz) {
    if ((poz > 1 && ((Token) tokeni.get(poz - 2)).mark != 'F' && ((Token) tokeni.get(poz - 1)).mark == '('
        && ((Token) tokeni
            .get(poz + 1)).mark == ')')
        || (poz == 1 && ((Token) tokeni.get(0)).mark == '(' && ((Token) tokeni
            .get(2)).mark == ')')) {
      tokeni.remove(poz + 1);
      tokeni.remove(poz - 1);
    }
  }

  private static double resultFunction(List<Token> tokeni, int function_position) {
    double result = 0.0D;
    Token t = tokeni.get(function_position);
    String fn = ((Function) t.tokenP).functionP;
    t = tokeni.get(function_position + 2);
    double operand = valueOperand(t);
    if ("log10".equals(fn)) {
      result = Matematika.log10(operand);
    } else if ("log2".equals(fn)) {
      result = Matematika.log2(operand);
    } else if ("log".equals(fn)) {
      result = Math.log(operand);
    } else if ("exp10".equals(fn)) {
      result = Matematika.exp10(operand);
    } else if ("exp2".equals(fn)) {
      result = Matematika.exp2(operand);
    } else if ("exp".equals(fn)) {
      result = Math.exp(operand);
    } else if ("sqrt".equals(fn)) {
      result = Math.sqrt(operand);
    } else if ("cur".equals(fn)) {
      result = Matematika.cur(operand);
    } else if ("sum".equals(fn)) {
      result = Matematika.sum(tokeni, function_position);
    } else if ("avg".equals(fn)) {
      result = Matematika.avg(tokeni, function_position);
    } else if ("min".equals(fn)) {
      result = Matematika.min(tokeni, function_position);
    } else if ("max".equals(fn)) {
      result = Matematika.max(tokeni, function_position);
    } else if ("stddev".equals(fn)) {
      result = Matematika.stddev(tokeni, function_position);
    } else if ("count".equals(fn)) {
      result = Matematika.count(tokeni, function_position);
    } else if ("abs".equals(fn)) {
      result = Math.abs(operand);
    } else if ("ceil".equals(fn)) {
      result = Math.ceil(operand);
    } else if ("fact".equals(fn)) {
      result = Matematika.fact((long) operand);
    } else if ("floor".equals(fn)) {
      result = Math.floor(operand);
    } else if ("pow".equals(fn)) {
      t = tokeni.get(function_position + 4);
      double operand2 = valueOperand(t);
      result = Matematika.potenciranje(operand, operand2);
    } else if ("random".equals(fn)) {
      result = Math.random() * operand;
    } else if ("round".equals(fn)) {
      t = tokeni.get(function_position + 4);
      byte operand2 = (byte) (int) valueOperand(t);
      String s = Rounding.round(formattedNumber(operand), operand2);
      result = Tokenzation.stringToDouble(s);
    } else if ("trunc".equals(fn)) {
      t = tokeni.get(function_position + 4);
      byte operand2 = (byte) (int) valueOperand(t);
      String s = Rounding.trunc(formattedNumber(operand), operand2);
      result = Tokenzation.stringToDouble(s);
    } else if ("sign".equals(fn)) {
      result = Matematika.sign(operand);
    } else if ("frac".equals(fn)) {
      result = Matematika.frac(operand);
    } else if ("hypot".equals(fn)) {
      t = tokeni.get(function_position + 4);
      double operand2 = valueOperand(t);
      result = Matematika.hypot(operand, operand2);
    } else if ("deg".equals(fn)) {
      result = Math.toDegrees(operand);
    } else if ("rad".equals(fn)) {
      result = Math.toRadians(operand);
    }
    return result;
  }

  public static List<String> result(List<Token> tokeni) {
    List<String> v = new ArrayList<>();
    izbacivanjeVisestrukihZagrada(tokeni);
    for (int j = 0; j < tokeni.size(); j++)
      throwOutParentheses(tokeni, j);
    do {
      String meduresult = spojTokena(tokeni);
      v.add(meduresult);
    } while (calculationStep(tokeni));
    Token t = tokeni.get(0);
    if (t.mark != 'D') {
      double x = valueOperand(t);
      tokeni.set(0, new Token(Double.valueOf(x), 'D', 0, 0));
      v.set(0, spojTokena(tokeni));
    }
    return v;
  }

  public static List<String> ispisTokena(List<Token> tokeni) {
    List<String> result = new ArrayList<>();
    String izraz = null;
    for (int i = 0; i < tokeni.size(); i++) {
      Token t = tokeni.get(i);
      izraz = t.tokenP.toString();
      if (izraz.endsWith(".0"))
        izraz = izraz.substring(0, izraz.length() - 2);
      izraz = izraz + " ; " + ExpressionCheck.nazivTokena(t.mark);
      result.add(izraz);
      izraz = null;
    }
    return result;
  }

  public static int dodjeljivanje(String s) {
    int i = 0;
    try {
      while (Character.isDigit(s.charAt(i)) || Character.isUpperCase(s.charAt(i))
          || Character.isWhitespace(s.charAt(i)))
        i++;
      if (s.charAt(i) == '=' && s.charAt(i + 1) != '=' && Character.isUpperCase(s.charAt(0)))
        return i;
    } catch (Exception e) {
      return 0;
    }
    return 0;
  }

  public static Span rangeParenthes(List<Token> tokeni) {
    Span raspon = null;
    int poz_zatvor_zagrade = positionOfTheFirstClosedParenthesis(tokeni);
    if (poz_zatvor_zagrade != 0) {
      int poz_otvor_zagrade = positionOpenParentheses(tokeni, poz_zatvor_zagrade);
      raspon = new Span(poz_otvor_zagrade, poz_zatvor_zagrade);
    }
    return raspon;
  }

  public static int operationPosition(List<Token> tokeni, Span raspon_zagrada) {
    byte max_prioritet = Byte.MAX_VALUE;
    int max_pozicija = 0;
    for (int i = raspon_zagrada.beginning + 2; i <= raspon_zagrada.end - 2; i++) {
      Token t = tokeni.get(i);
      if (t.mark == 'P') {
        byte prioritet = ((Operator) t.tokenP).priority;
        String operator = ((Operator) t.tokenP).operatorStr;
        if (prioritet < max_prioritet
            || (("^".equals(operator) || "**".equals(operator)) && prioritet == max_prioritet)) {
          max_prioritet = prioritet;
          max_pozicija = i;
        }
      }
    }
    return max_pozicija;
  }

  public static void izbacivanjeVisestrukihZagrada(List<Token> tokeni) {
    List<Token> pomocni = new ArrayList<>(tokeni);
    Span r;
    while ((r = rangeParenthes(pomocni)) != null) {
      if (r.beginning != 0 && r.end != pomocni.size() - 1 && ((Token) pomocni.get(r.beginning - 1)).mark == '('
          && ((Token) pomocni
              .get(r.end + 1)).mark == ')') {
        pomocni.remove(r.end + 1);
        pomocni.remove(r.beginning - 1);
        tokeni.remove(r.end + 1);
        tokeni.remove(r.beginning - 1);
        continue;
      }
      if (r.beginning == 0 && r.end == tokeni.size() - 1) {
        tokeni.remove(r.end);
        tokeni.remove(r.beginning);
        continue;
      }
      pomocni.set(r.end, new Token(null, false, 0, 0));
      pomocni.set(r.beginning, new Token(null, false, 0, 0));
    }
  }

  public static String spojTokena(List<Token> tokeni) {
    String izraz = "";
    for (int i = 0; i < tokeni.size(); i++) {
      Token t = tokeni.get(i);
      if (t.mark == 'D') {
        double broj = ((Double)t.tokenP).doubleValue();
        izraz = izraz + formatiraniBroj(broj);
      } else {
        izraz = izraz + t.tokenP;
      } 
      if (izraz.endsWith(".0"))
        izraz = izraz.substring(0, izraz.length() - 2); 
      izraz = izraz + " ";
    } 
    return izraz;
  }

  public static String formatiraniBroj(double d) {
    if (1.0E-15D < Math.abs(d) && Math.abs(d) < 1.0E15D) {
      DecimalFormat df = new DecimalFormat("#.###############################");
      return df.format(d).replace(',', '.');
    } 
    return String.valueOf(d);
  }

}
