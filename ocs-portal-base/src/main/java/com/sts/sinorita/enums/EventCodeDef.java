package com.sts.sinorita.enums;

public enum EventCodeDef {
  RECHARGE("0006"),
  STATUS_CHG("0005"),
  DEP_PROD_CHG("0007"),
  PRICE_PLAN_CHG("0008"),
  REALTIME_NOTIFY("0011"),
  LOWBALANCE("0004"),
  REGISTER_EMERGENCY_CREDIT("0012"),
  RETURN_EMERGENCY_CREDIT("0013");

  private String eventCode;

  EventCodeDef(String eventCode) {
    this.eventCode = eventCode;
  }

  public String toString() {
    return this.eventCode;
  }
}
