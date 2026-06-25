package com.ocs.portal.projection.pricePlan;

public interface BalSubsEventProjection {
    Long getSubsEventId();
    Long getBalThresholdId();
    String getEventName();
    String getRefAttr();
    Character getTriggerMode();
    Character getAntibillShock();
    String getExtAttr();
    Long getNotifyParamsId();
}
