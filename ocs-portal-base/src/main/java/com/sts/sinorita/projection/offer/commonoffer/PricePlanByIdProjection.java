package com.sts.sinorita.projection.offer.commonoffer;

import java.time.LocalDate;

public interface PricePlanByIdProjection {
    Integer getOfferId();
    Character getOfferType();
    String getOfferName();
    String getOfferCode();
    String getComments();
    Long getSaleListPrice();
    Long getRentListPrice();
    LocalDate getEffDate();
    LocalDate getExpDate();
    String getAutoContinueFlag();
    Integer getCycleQuantity();
    Character getTimeUnit();
    Integer getExpOff();
    Character getExpTimeUnit();
    Character getDuplicateFlag();
    String getEffType();
    Integer getPricePlanId();
    Character getApplyLevel();
    Integer getServType();
    Integer getPriority();
    Character getIsPackage();
    Character getPricePlanType();
    LocalDate getCreatedDate();
    Character getAgreementEffType();
    Character getSalePriceGstType();
    Character getRentPriceGstType();
    Character getGroupType();
    Integer getUpperLimit();
    Integer getLowerLimit();
}
