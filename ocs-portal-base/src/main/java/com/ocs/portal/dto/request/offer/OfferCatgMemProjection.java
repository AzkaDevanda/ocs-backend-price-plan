package com.ocs.portal.dto.request.offer;

import java.time.LocalDate;

public interface OfferCatgMemProjection {
    Integer getSeq();

    Integer getOfferCatgMemId();

    Integer getOfferId();

    Character getOfferType();

    String getOfferName();

    String getOfferCode();

    LocalDate getEffDate();

    LocalDate getExpDate();

    Character getDuplicateFlag();

    Integer getExpOff();

    Character getExpTimeUnit();

    Character getIsPackage();

    Character getApplyLevel();

    Character getPolicyFlag();

    Character getWarnLevel();

    Character getPricePlanType();
}
