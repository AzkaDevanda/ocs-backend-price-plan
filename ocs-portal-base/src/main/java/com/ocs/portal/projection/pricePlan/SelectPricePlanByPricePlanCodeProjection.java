package com.ocs.portal.projection.pricePlan;

import java.time.LocalDateTime;

public interface SelectPricePlanByPricePlanCodeProjection {
  Long getPricePlanId();

  String getApplyLevel();

  String getPricePlanName();

  String getComments();

  String getState();

  LocalDateTime getStateDate();

  Long getPriority();

  String getPricePlanCode();

  Long getSpId();
}
