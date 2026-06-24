package com.sts.sinorita.projection.offer.offercatgmem;

import java.time.LocalDate;

public interface IndepOfferListByCatgIdProjection {
  Integer getOfferCatgMemId();

  Integer getChildOfferCatgId();

  Integer getSeq();

  Integer getOfferId();

  Integer getOfferType();

  String getOfferName();

  String getOfferCode();

  LocalDate getEffDate();

  LocalDate getExpDate();

  String getEffType();

  String getExpOff();

  String getTimeUnit();

  String getComments();

  String getProdType();

  Integer getIndepProdSpecId();

  Integer getServType();

  String getPaidFlag();

  Integer getBrandPricePlanId();

  String getServTypeName();

  Integer getServTypePaidFlag();

  String getNetworkType();
}
