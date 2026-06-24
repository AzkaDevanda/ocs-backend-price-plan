package com.sts.sinorita.projection.pricePlan.priceplanpackage;

public interface BalAdviceProjection {
    Long getBalThresholdId();
    String getAdviceType();
    String getAdviceTypeName();
    String getTriggerMode();
    Long getAdviceEventId();
    String getAdviceEventName();
    Long getNotifyParamsId();
    String getTriggerNotification();

}
