package com.ocs.portal.projection.acct;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface QryAcctPricePlanProjection {
    Integer getPricePlanId();

    String getApplyLevel();

    Integer getPriority();

    String getServType();

    Character getIsPackage();

    Integer getWarnLevel();

    Integer getPricePlanType();

    String getPricePlanTypeName();

    String getPricePlanName();

    String getPricePlanCode();

    Long getSaleListPrice();

    Long getRentListPrice();

    Character getEffType();

    Character getAutoContinueFlag();

    Integer getCycleQuantity();

    String getTimeUnit();

    Character getDuplicateFlag();

    LocalDate getEffDate();

    LocalDate getExpDate();

    String getComments();

    String getState();

    LocalDate getStateDate();

    LocalDate getCreatedDate();
}
