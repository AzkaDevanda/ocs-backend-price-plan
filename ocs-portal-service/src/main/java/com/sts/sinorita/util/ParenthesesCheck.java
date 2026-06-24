package com.sts.sinorita.util;

import java.util.Stack;

final class ParenthesesCheck {
  public static boolean isOpenParentheses(char c) {
    return (c == '(' || c == '[' || c == '{');
  }
  
  public static boolean isClosedParentheses(char c) {
    return (c == ')' || c == ']' || c == '}');
  }
  
  private static boolean parenthesesMatch(char open, char closed) {
    if (open == '(' && closed == ')')
      return true; 
    if (open == '[' && closed == ']')
      return true; 
    if (open == '{' && closed == '}')
      return true; 
    return false;
  }
  
  public static boolean parenthesesValid(String expression) {
    Stack<Character> s = new Stack<>();
    boolean povrat = true;
    for (int i = 0; i < expression.length(); i++) {
      char trenutni_znak = expression.charAt(i);
      if (isOpenParentheses(trenutni_znak)) {
        Character c = new Character(trenutni_znak);
        s.push(c);
      } else if (isClosedParentheses(trenutni_znak)) {
        if (s.isEmpty()) {
          povrat = false;
          break;
        } 
        Character c = s.pop();
        char znak = c.charValue();
        if (!parenthesesMatch(znak, trenutni_znak)) {
          povrat = false;
          break;
        } 
      } 
    } 
    if (!s.isEmpty())
      povrat = false; 
    return povrat;
  }

}
