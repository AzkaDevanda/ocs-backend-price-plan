package com.ocs.portal.projection.pricePlan;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SubsPricePlanProjection {
    Integer getPricePlanId();

    String getApplyLevel();

    Integer getPriority();

    String getServType();

    String getIsPackage();

    String getWarnLevel();

    String getPricePlanType();

    String getPricePlanTypeName();

    String getPricePlanName();

    String getPricePlanCode();

    Double getSaleListPrice();

    Double getRentListPrice();

    String getEffType();

    String getAutoContinueFlag();

    Integer getCycleQuantity();

    Character getTimeUnit();

    Character getDuplicateFlag();

    LocalDate getEffDate();

    LocalDate getExpDate();

    String getComments();

    String getState();

    LocalDate getStateDate();

    LocalDate getCreatedDate();
}
