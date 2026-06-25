package com.ocs.portal.projection.pricePlan.trigger;


public interface BalSubsEventProjection {
    Integer getSubsEventId();
    Integer getBalThresholdId();
    String getEventName();
    String getRefAttr();
    Character getTriggerMode();
    Character getAntibillShock();
    String getExtAttr();
    Integer getNotifyParamsId();
}
