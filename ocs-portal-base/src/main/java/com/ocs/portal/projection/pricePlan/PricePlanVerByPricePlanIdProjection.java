package com.ocs.portal.projection.pricePlan;

import java.time.LocalDate;

public interface PricePlanVerByPricePlanIdProjection {

    Integer getOfferVerId();

    Integer getOfferId();

    LocalDate getEffDate();

    LocalDate getExpDate();

    Integer getSeq();

    Character getState();

    Integer getRefOfferVerId();

}
