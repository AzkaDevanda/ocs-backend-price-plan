package com.sts.sinorita.projection.pricePlan;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CopyFromProjection {
    Integer getPricePlanId();
    String getPricePlanName();
    Integer getOfferVerId();
    LocalDateTime getEffDate();
    LocalDateTime getExpDate();
    Integer getSeq();
}
