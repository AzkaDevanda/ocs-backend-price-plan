package com.sts.sinorita.projection.pricePlan;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public interface IndepProdSpecProjection {
    Long getIndepProdSpecId();

    Long getBrandPricePlanId();

    Integer getServType();

    Character getPaidFlag();

    Character getNetworkType();

    Character getServPaidFlag();

    Long getOfferId();

    String getOfferName();

    String getOfferCode();

    Character getSaleListPrice();

    Character getRentListPrice();

    LocalDate getEffDate();

    LocalDate getExpDate();

    LocalDate getCreatedDate();

    Character getState();

    LocalDate getStateDate();

    String getEffType();

    Character getAutoContinueFlag();

    Integer getCycleQuantity();

    String getTimeUnit();

    Character getDuplicateFlag();

    String getComments();

    String getProdType();

    String getLifecycleType();
}
