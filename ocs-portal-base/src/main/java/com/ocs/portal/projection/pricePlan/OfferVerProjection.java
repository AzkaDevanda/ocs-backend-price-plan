package com.ocs.portal.projection.pricePlan;

import jakarta.persistence.Column;
import lombok.Builder;

import java.time.LocalDate;

public interface OfferVerProjection {
    Long getDependProdSpecId();
    Long getOfferId();
    String getOfferName();
    String getNetworkType();
    String getNetworkTypeName();

}
