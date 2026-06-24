package com.sts.sinorita.projection.offer.offerrela;

import java.time.LocalDate;

public interface DependProdDetailByOfferIdProjection {
    Long getOfferId();
    String getOfferType();
    String getOfferName();
    String getComments();
    String getOfferCode();
    Long getSaleListPrice();
    Long getRentListPrice();
    LocalDate getEffDate();
    LocalDate getExpDate();
    LocalDate getCreatedDate();
    Character getState();
    LocalDate getStateDate();
    String getEffType();
    String getAutoContinueFlag();
    Integer getCycleQuantity();
    Character getTimeUnit();
    Character getDuplicateFlag();
    Integer getExpOff();
    Character getExpTimeUnit();
    Character getAgreementEffType();
    Character getSalePriceGstType();
    Character getRentPriceGstType();
    Integer getServType();
    String getIsPackage();
    Integer getLifecycleType();
    String getGroupType();
    Integer getUpperLimit();
    Integer getLowerLimit();
    String getNetworkType();
}
