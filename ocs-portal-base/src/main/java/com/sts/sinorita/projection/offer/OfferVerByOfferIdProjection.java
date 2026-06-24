package com.sts.sinorita.projection.offer;

import java.time.LocalDateTime;

public interface OfferVerByOfferIdProjection {
  Integer getOfferVerId();

  Integer getOfferId();

  LocalDateTime getExpDate();

  LocalDateTime getEffDate();

  String getName();

  String getType();
}
