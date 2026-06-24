package com.sts.sinorita.dto.request.balanceAdjustment;

public final class Span {
  public int beginning;

  public int end;

  public Span() {
    this.beginning = 0;
    this.end = 0;
  }

  public Span(int beginning, int end) {
    this.beginning = beginning;
    this.end = end;
  }

  public String toString() {
    String s = "( " + this.beginning + ", " + this.end + " )";
    return s;
  }
}
