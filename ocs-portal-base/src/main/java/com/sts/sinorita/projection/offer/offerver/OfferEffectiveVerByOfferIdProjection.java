package com.sts.sinorita.projection.offer.offerver;

import java.time.LocalDateTime;

public interface OfferEffectiveVerByOfferIdProjection {
  Integer getOfferVerId();

  Integer getOfferId();

  LocalDateTime getEffDate();

  LocalDateTime getExpDate();

  Integer getSeq();

  Integer getSpId();
}
