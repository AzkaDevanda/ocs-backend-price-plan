package com.sts.sinorita.projection.offer.offergroup;

import java.time.LocalDate;

public interface OfferGroupAndMemberProjection {
  Long getOfferGroupId();

  String getOfferGroupName();

  String getOfferGroupCode();

  String getOfferGroupType();

  String getGroupType();

  Integer getUpperLimit();

  Integer getLowerLimit();

  LocalDate getEffDate();

  LocalDate getExpDate();

  LocalDate getCreatedDate();

  String getState();

  LocalDate getStateDate();

  String getShareFlag();

  Long getIndepProdSpecId();

  Long getOfferVerId();

  String getComments();

  String getNetworkType();
}
