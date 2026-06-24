package com.sts.sinorita.mapper.pricePlan.trigger;

public interface QrySubsEvent {
    Integer getSubsEventId();
    String getEventName();
    String getComments();
    Integer getPriority();
    String getStateSet();
}
