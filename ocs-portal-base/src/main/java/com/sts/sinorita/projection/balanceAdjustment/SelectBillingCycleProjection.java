package com.sts.sinorita.projection.balanceAdjustment;

import java.time.LocalDateTime;

public interface SelectBillingCycleProjection {
    Long getBillingCycleId();

    Long getBillingCycleTypeId();

    LocalDateTime getCycleBeginDate();

    LocalDateTime getCycleEndDate();

    String getState();

    LocalDateTime getDebtDate();

    Long getSpId();

    LocalDateTime getRunDate();

    LocalDateTime getPostingDate();

    LocalDateTime getInvoiceDate();

    LocalDateTime getOriginDate();
}
