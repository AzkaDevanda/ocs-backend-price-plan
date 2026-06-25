package com.ocs.portal.dto.request.balanceAdjustment;

public final class Operator {
  public String operatorStr;
  
  public byte priority;
  
  public Operator(String operator, byte priority) {
    this.operatorStr = operator;
    this.priority = priority;
  }
  
  public String toString() {
    return this.operatorStr;
  }
}
