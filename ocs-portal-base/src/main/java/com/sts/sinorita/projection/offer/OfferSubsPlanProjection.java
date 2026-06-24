package com.sts.sinorita.projection.offer;

import java.time.LocalDate;

public interface OfferSubsPlanProjection {
    Integer getSubsPlanId();

    String getSubsPlanName();

    Integer getIndepProdSpecId();

    Integer getOfferVerId();

    LocalDate getEffDate();

    LocalDate getExpDate();

    Integer getOfferGroupId();
}
