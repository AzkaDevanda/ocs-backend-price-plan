package com.sts.sinorita.projection.offer.offerrela;

import java.time.LocalDate;

public interface DependOfferListByCatgProjection {
    Integer getSeq();
    Integer getOfferCatgMemId();
    Integer getOfferId();
    Character getOfferType();
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
    Integer getExpOff();
    Character getTimeUnit();
    Character getAutoContinueFlag();
    Integer getCycleQuantity();
    Character getDuplicateFlag();
    Integer getSpId();
    Character getExpTimeUnit();
    Character getAgreementEffType();
    String getSalesRuleScript();
    Character getSalePriceGstType();
    Character getRentPriceGstType();
    Character getProdType();
    Character getPublishState();
    LocalDate getPublishStateDate();
    String getIsPackage();
    Integer getServType();
    String getServTypeName();
}
