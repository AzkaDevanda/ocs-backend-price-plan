package com.sts.sinorita.projection.acct;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BillingCycleListProjection {
    Integer getBillingCycleId();
    Integer getBillingCycleTypeId();
    LocalDateTime getCycleBeginDate();
    LocalDateTime getCycleEndDate();
    String getState();
    String getStateFlag();
    LocalDateTime getDebtDate();
    LocalDateTime getRunDate();
    LocalDateTime getDocumentDate();
    LocalDateTime getPostingDate();
    LocalDateTime getInvoiceDate();
    LocalDateTime getOriginDate();
    LocalDateTime getNotificationDate();
}
