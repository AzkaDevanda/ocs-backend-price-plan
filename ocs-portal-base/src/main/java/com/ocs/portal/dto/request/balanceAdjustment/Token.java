package com.ocs.portal.dto.request.balanceAdjustment;

public final class Token {

  public Object tokenP;

  public char mark;

  public int position;

  public int length;

  public Token(Object token, char mark, int position, int length) {
    this.tokenP = token;
    this.mark = mark;
    this.position = position;
    this.length = length;
  }

  public Token(Object token, boolean b, int position2, int length2) {
    //TODO Auto-generated constructor stub
  }

  public String toString() {
    return this.tokenP.toString() + " ; " + this.mark + " ; " + this.position + " ; " + this.length + "\n";
  }
}
