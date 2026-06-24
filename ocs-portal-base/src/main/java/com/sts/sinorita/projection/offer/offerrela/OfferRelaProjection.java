package com.sts.sinorita.projection.offer.offerrela;

import java.time.LocalDateTime;

public interface OfferRelaProjection {
  String getRelaType();

  Integer getOriLowerLimit();

  Integer getOriUpperLimit();

  String getOriOfferType();

  String getOriOfferGroupOfferType();

  String getDestOfferType();

  String getDestOfferGroupOfferType();

  String getOriOfferName();

  String getOriOfferGroupName();

  String getDestOfferName();

  String getDestOfferGroupName();

  String getOriIndOfferName();

  String getOriSubsPlanName();

  LocalDateTime getOriEffDate();

  LocalDateTime getOriExpDate();

  String getDestIndOfferName();

  String getDestSubsPlanName();

  LocalDateTime getDestEffDate();

  LocalDateTime getDestExpDate();
}
