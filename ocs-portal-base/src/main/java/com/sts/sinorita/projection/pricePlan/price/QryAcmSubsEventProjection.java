package com.sts.sinorita.projection.pricePlan.price;

public interface QryAcmSubsEventProjection {

    Integer getAcmThresholdId();

    Integer getSubsEventId();

    String getEventName();

    String getExtAttr();

    Character getTriggerMode();

    Character getAntibillShock();

    Integer getNotifyParamsId();

}
