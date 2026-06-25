package com.ocs.portal.projection.pricePlan.price;

import java.time.LocalDate;

public interface AcmListProjection {
    Long getPriceVerId();

    String getAccumulation();

    Integer getRum();

    Integer getResourceId();

    String getResourceName();

    Integer getReAttr();

    String getReAttrName();

    LocalDate getEffDate();

    LocalDate getExpDate();

    String getAcmName();

    Integer getPriceId();

    Integer getSrcPriceId();

    String getComments();

    Integer getRefValueId();

    String getShareFlag();

    Integer getRatePlanId();

    String getRatePlanType();

    Integer getOfferVerId();

    Integer getMappingId();
}
