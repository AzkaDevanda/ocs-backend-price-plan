package com.ocs.portal.util;

import java.math.BigDecimal;
import java.util.List;

import com.ocs.portal.dto.request.balanceAdjustment.Token;

public final class Matematika {
  public static double potenciranje(double a, double n) {
    if ((int)n != n)
      return Math.pow(a, n); 
    double rezultat = 1.0D;
    for (int i = 1; i <= n; i++) {
      rezultat = mnozenje(rezultat, a);
      if (Double.isInfinite(rezultat))
        break; 
    } 
    return rezultat;
  }
  
  public static double oduzimanje(double a, double b) {
    double rezultat;
    try {
      BigDecimal x = new BigDecimal(Calculation.formattedNumber(a));
      BigDecimal y = new BigDecimal(Calculation.formattedNumber(b));
      rezultat = x.subtract(y).doubleValue();
    } catch (Exception e) {
      rezultat = a - b;
    } 
    return rezultat;
  }
  
  public static double zbrajanje(double a, double b) {
    double rezultat;
    try {
      BigDecimal x = new BigDecimal(Calculation.formattedNumber(a));
      BigDecimal y = new BigDecimal(Calculation.formattedNumber(b));
      rezultat = x.add(y).doubleValue();
    } catch (Exception e) {
      rezultat = a + b;
    } 
    return rezultat;
  }
  
  public static double mnozenje(double a, double b) {
    double rezultat;
    try {
      BigDecimal x = new BigDecimal(Calculation.formattedNumber(a));
      BigDecimal y = new BigDecimal(Calculation.formattedNumber(b));
      rezultat = x.multiply(y).doubleValue();
      double provjera = a * b;
      if (String.valueOf(provjera).length() < String.valueOf(rezultat).length())
        rezultat = provjera; 
    } catch (Exception e) {
      rezultat = a * b;
    } 
    return rezultat;
  }
  
  public static double dijeljenje(double a, double b) {
    double rezultat;
    try {
      BigDecimal x = new BigDecimal(Calculation.formattedNumber(a));
      BigDecimal y = new BigDecimal(Calculation.formattedNumber(b));
      rezultat = x.divide(y, 17, 7).doubleValue();
    } catch (Exception e) {
      rezultat = a / b;
    } 
    return rezultat;
  }
  
  public static double degToRad(double x) {
    return Math.toRadians(x);
  }
  
  public static double degToGrad(double x) {
    return x * 10.0D / 9.0D;
  }
  
  public static double radToDeg(double x) {
    return Math.toDegrees(x);
  }
  
  public static double radToGrad(double x) {
    return Math.toDegrees(x) * 10.0D / 9.0D;
  }
  
  public static double gradToDeg(double x) {
    return x * 9.0D / 10.0D;
  }
  
  public static double gradToRad(double x) {
    return Math.toRadians(x * 9.0D / 10.0D);
  }
  
  public static double sign(double x) {
    if (x < 0.0D)
      return -1.0D; 
    if (x > 0.0D)
      return 1.0D; 
    return 0.0D;
  }
  
  public static double hypot(double x, double y) {
    return Math.sqrt(x * x + y * y);
  }
  
  public static double frac(double x) {
    return oduzimanje(Math.abs(x), Math.rint(Math.abs(x) - 0.5D));
  }
  
  public static double cot(double x) {
    return 1.0D / Math.tan(x);
  }
  
  public static double sec(double x) {
    return 1.0D / Math.cos(x);
  }
  
  public static double csc(double x) {
    return 1.0D / Math.sin(x);
  }
  
  public static double acot(double x) {
    return Math.atan(1.0D / x);
  }
  
  public static double asec(double x) {
    return Math.acos(1.0D / x);
  }
  
  public static double acsc(double x) {
    return Math.asin(1.0D / x);
  }
  
  public static double sinh(double x) {
    return (Math.pow(Math.E, x) - Math.pow(Math.E, -x)) / 2.0D;
  }
  
  public static double cosh(double x) {
    return (Math.pow(Math.E, x) + Math.pow(Math.E, -x)) / 2.0D;
  }
  
  public static double tanh(double x) {
    return sinh(x) / cosh(x);
  }
  
  public static double coth(double x) {
    return cosh(x) / sinh(x);
  }
  
  public static double sech(double x) {
    return 2.0D / (Math.pow(Math.E, x) + Math.pow(Math.E, -x));
  }
  
  public static double csch(double x) {
    return 2.0D / (Math.pow(Math.E, x) - Math.pow(Math.E, -x));
  }
  
  public static double asinh(double x) {
    return Math.log(x + Math.sqrt(x * x + 1.0D));
  }
  
  public static double acosh(double x) {
    return Math.log(x + Math.sqrt(x * x - 1.0D));
  }
  
  public static double atanh(double x) {
    return Math.log((1.0D + x) / (1.0D - x)) / 2.0D;
  }
  
  public static double acoth(double x) {
    return atanh(1.0D / x);
  }
  
  public static double asech(double x) {
    return acosh(1.0D / x);
  }
  
  public static double acsch(double x) {
    return asinh(1.0D / x);
  }
  
  public static double log10(double x) {
    return Math.log(x) / Math.log(10.0D);
  }
  
  public static double log2(double x) {
    return Math.log(x) / Math.log(2.0D);
  }
  
  public static double exp10(double x) {
    return Math.pow(10.0D, x);
  }
  
  public static double exp2(double x) {
    return Math.pow(2.0D, x);
  }
  
  public static double cur(double x) {
    return Math.pow(x, 0.3333333333333333D);
  }
  
  public static double fact(long n) {
    double rezultat = 1.0D;
    for (int i = 2; i <= n; i++) {
      rezultat *= i;
      if (Double.isInfinite(rezultat))
        break; 
    } 
    return rezultat;
  }
  
  public static double comb(long n, long r) {
    return fact(n) / fact(r) * fact(n - r);
  }
  
  public static double combr(long n, long r) {
    return comb(n + r - 1L, r);
  }
  
  public static double perm(long n) {
    return fact(n);
  }
  
  public static double permr(long n, long r) {
    return fact(n) / fact(r);
  }
  
  public static double var(long n, long r) {
    return comb(n, r) * fact(r);
  }
  
  public static double varr(long n, long r) {
    return Math.pow(n, r);
  }
  
  public static double max(List<Token> tokeni, int pozicija_funkcije) {
    int i = pozicija_funkcije + 2;
    Token t = tokeni.get(i);
    double maksimum = Calculation.valueOperand(t);
    int kraj = ExpressionCheck.endFunction(tokeni, pozicija_funkcije);
    for (i += 2; i < kraj; i += 2) {
      t = tokeni.get(i);
      double broj = Calculation.valueOperand(t);
      if (broj > maksimum)
        maksimum = broj; 
    } 
    return maksimum;
  }
  
  public static double min(List<Token> tokeni, int pozicija_funkcije) {
    int i = pozicija_funkcije + 2;
    Token t = tokeni.get(i);
    double minimum = Calculation.valueOperand(t);
    int kraj = ExpressionCheck.endFunction(tokeni, pozicija_funkcije);
    for (i += 2; i < kraj; i += 2) {
      t = tokeni.get(i);
      double broj = Calculation.valueOperand(t);
      if (broj < minimum)
        minimum = broj; 
    } 
    return minimum;
  }
  
  public static double sum(List<Token> tokeni, int pozicija_funkcije) {
    double suma = 0.0D;
    int kraj = ExpressionCheck.endFunction(tokeni, pozicija_funkcije);
    for (int i = pozicija_funkcije + 2; i < kraj; i += 2) {
      Token t = tokeni.get(i);
      double broj = Calculation.valueOperand(t);
      suma = zbrajanje(suma, broj);
    } 
    return suma;
  }
  
  public static double avg(List<Token> tokenList, int pozicija_funkcije) {
    int n = 0;
    double suma = 0.0D;
    int kraj = ExpressionCheck.endFunction(tokenList, pozicija_funkcije);
    for (int i = pozicija_funkcije + 2; i < kraj; i += 2) {
      Token t = tokenList.get(i);
      double broj = Calculation.valueOperand(t);
      suma = zbrajanje(suma, broj);
      n++;
    } 
    return dijeljenje(suma, n);
  }
  
  public static double count(List<Token> tokeni, int pozicija_funkcije) {
    int n = 0;
    int kraj = ExpressionCheck.endFunction(tokeni, pozicija_funkcije);
    for (int i = pozicija_funkcije + 2; i < kraj; i += 2)
      n++; 
    return n;
  }
  
  public static double stddev(List<Token> tokeni, int pozicija_funkcije) {
    int n = 0;
    double suma = 0.0D;
    double suma_kvadrata = 0.0D;
    int kraj = ExpressionCheck.endFunction(tokeni, pozicija_funkcije);
    for (int i = pozicija_funkcije + 2; i < kraj; i += 2) {
      Token t = tokeni.get(i);
      double broj = Calculation.valueOperand(t);
      suma += broj;
      suma_kvadrata += broj * broj;
      n++;
    } 
    return Math.sqrt((n * suma_kvadrata - suma * suma) / (n * (n - 1)));
  }
  
  public static char znamenka(int x) {
    switch (x) {
      case 10:
        return 'A';
      case 11:
        return 'B';
      case 12:
        return 'C';
      case 13:
        return 'D';
      case 14:
        return 'E';
      case 15:
        return 'F';
    } 
    return (char)(x + 48);
  }
  
  public static String brojBaza(long b, int baza) {
    String s = "";
    b = Math.abs(b);
    if (b == 0L)
      return "0"; 
    while (b != 0L) {
      s = znamenka((int)(b % baza)) + s;
      b /= baza;
    } 
    return s;
  }
  
  public static long bazaBroj(String s, int baza) {
    long r = 0L;
    for (int i = s.length() - 1, j = 0; i >= 0; i--, j++)
      r += tezinaZnamenke(s.charAt(i)) * (long)Math.pow(baza, j); 
    if (r > 4294967295L)
      return 2147483647L; 
    if (r > 2147483647L)
      return r - 4294967296L; 
    return r;
  }
  
  public static int tezinaZnamenke(char c) {
    if (Character.isDigit(c))
      return c - 48; 
    if ('A' <= c && c <= 'F')
      return c - 55; 
    if ('a' <= c && c <= 'f')
      return c - 87; 
    return -1;
  }

}
