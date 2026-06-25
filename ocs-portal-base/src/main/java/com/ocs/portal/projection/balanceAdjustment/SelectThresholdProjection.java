package com.ocs.portal.projection.balanceAdjustment;

public interface SelectThresholdProjection {
  Long getSubsEventId();

  String getExtAttr();

  Long getAdviceType();

  Long getThresholdValue();

  Long getRatio();

  String getReAttr();

  Long getBalThresholdId();

  Long getTriggerId();

  Long getAcctResId();

  String getTriggerType();

  String getAcctResIdList();

  Long getPricePlanId();
}
