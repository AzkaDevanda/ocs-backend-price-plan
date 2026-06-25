package com.ocs.portal.dto.response.priceVer;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PriceProjection {
    Character getReType();

    Long getPriceVerId();

    LocalDate getEffDate();

    LocalDate getExpDate();

    Long getPriceId();

    String getPriceName();

    String getValueString();

    Integer getRum();

    String getAcctItemTypeName();

    String getReAttrName();
}
