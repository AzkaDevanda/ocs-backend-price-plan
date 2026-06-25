package com.ocs.portal.projection.accountConfig;

import java.time.LocalDate;
import java.util.Date;

public interface BillingCycleLastProjection {
    Date getCycleEndDate();
    Date getDebtDate();
}
