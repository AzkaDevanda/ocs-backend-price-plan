package com.ocs.portal.projection.offer.offercatgmem;

public interface PriceOfferListByCatgIdProjection {
  String getSeq();

  String getOfferCatgMemId();

  String getOfferId();

  String getOfferType();

  String getOfferName();

  String getOfferCode();

  String getEffDate();

  String getExpDate();

  String getDuplicateFlag();

  String getExpOff();

  String getExpTimeUnit();

  String getIsPackage();

  String getApplyLevel();

  String getPolicyFlag();

  String getWarnLevel();

  String getPricePlanType();
}
