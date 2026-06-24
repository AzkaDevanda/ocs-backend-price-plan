package com.sts.sinorita.util;

public final class Rounding {
  private static String broj;
  
  private static String decimale;
  
  private static String eksponent;
  
  private static int pozicijaTocke;
  
  public static String rpad(String s, char znak, int duzina) {
    int n = duzina - s.length();
    StringBuilder sBuilder = new StringBuilder(s);
    for (int i = 0; i < n; i++)
      sBuilder.append(znak); 
    s = sBuilder.toString();
    return s;
  }
  
  public static String trunc(String s, int p) {
    char predznak = '+';
    rastavljanje(s);
    if (s.charAt(0) == '-') {
      broj = broj.substring(1);
      predznak = '-';
      pozicijaTocke--;
    } 
    if (broj.charAt(0) != '0') {
      broj = "0" + broj;
      pozicijaTocke++;
    } 
    String spoj = broj + decimale;
    if (p >= decimale.length())
      return s; 
    if (p + pozicijaTocke < 1)
      return "0"; 
    broj = spoj.substring(0, pozicijaTocke + p);
    decimale = spoj.substring(pozicijaTocke + p);
    if (broj.length() < pozicijaTocke)
      broj = rpad(broj, '0', pozicijaTocke); 
    s = broj.substring(0, pozicijaTocke) + "." + broj.substring(pozicijaTocke) + "E" + eksponent;
    return predznak + s;
  }
  
  public static String round(String s, int p) {
    char predznak = '+';
    rastavljanje(s);
    if (s.charAt(0) == '-') {
      broj = broj.substring(1);
      predznak = '-';
      pozicijaTocke--;
    } 
    if (broj.charAt(0) != '0') {
      broj = "0" + broj;
      pozicijaTocke++;
    } 
    String spoj = broj + decimale;
    if (p >= decimale.length())
      return s; 
    if (p + pozicijaTocke < 1)
      return "0"; 
    broj = spoj.substring(0, pozicijaTocke + p);
    decimale = spoj.substring(pozicijaTocke + p);
    char z = decimale.charAt(0);
    if (z > '4') {
      int duzina = broj.length();
      long b = Long.parseLong(broj);
      b++;
      broj = rpad("0", '0', duzina - String.valueOf(b).length()) + b;
      if (String.valueOf(broj).length() > duzina)
        pozicijaTocke++; 
    } 
    if (broj.length() < pozicijaTocke)
      broj = rpad(broj, '0', pozicijaTocke); 
    s = broj.substring(0, pozicijaTocke) + "." + broj.substring(pozicijaTocke) + "E" + eksponent;
    return predznak + s;
  }
  
  public static void rastavljanje(String s) {
    broj = "";
    decimale = "";
    eksponent = "0";
    int pozicija_eksponenta = s.indexOf("E");
    if (pozicija_eksponenta != -1) {
      eksponent = s.substring(pozicija_eksponenta + 1);
      s = s.substring(0, pozicija_eksponenta);
    } 
    pozicijaTocke = s.indexOf(".");
    if (pozicijaTocke != -1) {
      decimale = s.substring(pozicijaTocke + 1);
      broj = s.substring(0, pozicijaTocke);
    } else {
      broj = s;
      pozicijaTocke = broj.length();
    } 
  }

}
