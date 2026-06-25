package com.ocs.portal.projection.offer.offerrela;

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
