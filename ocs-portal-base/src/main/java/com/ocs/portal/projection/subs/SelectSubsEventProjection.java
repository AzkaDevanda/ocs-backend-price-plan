package com.ocs.portal.projection.subs;

public interface SelectSubsEventProjection {
  Long getSubsEventId();

  String getEventName();

  String getComments();

  Long getPriority();

  String getStateSet();

  String getEventCatg();

  String getObjProdState();
}
