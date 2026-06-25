package com.ocs.portal.projection.offer;

import java.time.LocalDateTime;

public interface OfferVerByOfferIdProjection {
  Integer getOfferVerId();

  Integer getOfferId();

  LocalDateTime getExpDate();

  LocalDateTime getEffDate();

  String getName();

  String getType();
}
