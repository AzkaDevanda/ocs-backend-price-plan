package com.ocs.portal.dto.request.balanceAdjustment;

public final class Function {

  public String functionP;
  
  public int numberOfArguments;
  
  public Function(String function, int numberOfArguments) {
    this.functionP = function;
    this.numberOfArguments = numberOfArguments;
  }
  
  public String toString() {
    return this.functionP;
  }

}