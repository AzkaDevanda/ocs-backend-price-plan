package com.sts.sinorita.projection.pricePlan.price;

import java.time.LocalDate;

public interface QryPriceVerProjection {
    Integer getPriceVerId();

    Integer getRatePlanId();

    Integer getMappingId();

    LocalDate getEffDate();

    LocalDate getExpDate();
}
