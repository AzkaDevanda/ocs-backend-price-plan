package com.sts.sinorita.dto.request.balanceAdjustment;

public final class Variable {

  public String variableP;
  
  public double value;
  
  public Variable(String variable, double value) {
    this.variableP = variable;
    this.value = value;
  }
  
  public String toString() {
    return this.variableP;
  }
}
