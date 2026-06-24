package com.sts.sinorita.projection.offer.offerrela;

import java.time.LocalDate;

public interface OfferGroupForRelaProjection {
    Integer getOfferId();
    String getOfferName();
    String getIndOfferName();
    String getSubsPlanName();
    LocalDate getEffDate();
    LocalDate getExpDate();
    String getNetworkTypeName();
}
