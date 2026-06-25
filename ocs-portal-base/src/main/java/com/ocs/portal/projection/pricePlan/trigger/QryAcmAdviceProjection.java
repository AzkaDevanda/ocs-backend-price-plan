package com.ocs.portal.projection.pricePlan.trigger;

public interface QryAcmAdviceProjection {

    Integer getAcmThresholdId();

    Integer getAdviceType();

    String getAdviceTypeName();

    Character getTriggerMode();

    Integer getAdviceEventId();

    String getAdviceEventName();

    Integer getNotifyParamsId();

    String getTriggerNotification();

}
