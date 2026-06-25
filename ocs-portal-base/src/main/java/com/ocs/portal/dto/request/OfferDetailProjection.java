package com.ocs.portal.dto.request;

import java.time.LocalDate;

public interface OfferDetailProjection {
    Long getOfferId();
    Long getOfferVerId();
    String getOfferName();
    String getOfferCode();
    String getPricePlanTypeName();
    LocalDate getEffDate();
    Integer getPricePlanId();
    Character getApplyLevel();
}
