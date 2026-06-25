package com.ocs.portal.projection.pricePlan.price;

import java.time.LocalDate;

public interface PriceRatingProjection {
    Long getPriceId();

    String getPriceName();

    String getValue(); // dari rv.value_string

    String getPayIndicator();

    Integer getRum();

    Integer getAcctItemTypeId();

    String getRemarks();

    Integer getReAttr();

    LocalDate getEffDate();

    LocalDate getExpDate();
}
