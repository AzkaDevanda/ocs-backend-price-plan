package com.sts.sinorita.projection.offer;

import java.time.LocalDateTime;

public interface SelectProdSpecByProdIdProjection {
  Long getOfferId();

  String getOfferType();

  String getOfferName();

  String getComments();

  String getOfferCode();

  LocalDateTime getEffDate();

  LocalDateTime getExpDate();

  String getState();

  LocalDateTime getStateDate();

  Long getSpId();
}
