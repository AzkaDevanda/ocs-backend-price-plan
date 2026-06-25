package com.ocs.portal.projection.acct;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BillingCycleTypeListProjection {
    Integer getBillingCycleTypeId();
    Character getTimeUnit();
    String getBillingCycleTypeName();
    String getComments();
    Integer getQuantity();
    LocalDate getBeginDate();
    LocalDateTime getDebtDate();
    String getTimeUnitName();
    Character getOperator();
    String getBillingCycleTypeCode();
    LocalDate getRunDate();
    Character getProdType();
    Character getPostpaid();
    Character getCustType();
    String getCustTypeName();
}
