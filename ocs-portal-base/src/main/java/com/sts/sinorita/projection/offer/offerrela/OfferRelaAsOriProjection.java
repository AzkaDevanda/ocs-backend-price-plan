package com.sts.sinorita.projection.offer.offerrela;

import java.time.LocalDate;

public interface OfferRelaAsOriProjection {
  Integer getOfferRelaId();

  Integer getRelaType();

  Integer getDestOfferId();

  Integer getOriLowerLimit();

  Integer getOriUpperLimit();

  Integer getDestOfferType();

  Integer getDestOfferGroupOfferType();

  String getOriOfferName();

  String getDestOfferName();

  String getDestOfferCode();

  String getDestOfferGroupName();

  String getDestIndOfferName();

  String getDestSubsPlanName();

  LocalDate getDestEffDate();

  LocalDate getDestExpDate();
}
