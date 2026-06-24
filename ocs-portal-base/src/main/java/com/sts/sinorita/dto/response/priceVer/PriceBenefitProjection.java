package com.sts.sinorita.dto.response.priceVer;


import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PriceBenefitProjection {
    Character getReType();

    Long getPriceVerId();

    Integer getSubBalTypeId();

    LocalDate getEffDate();

    LocalDate getExpDate();

    Long getPriceId();

    String getPriceName();

    String getValueString();

    Integer getRum();

    String getAcctResName();

    String getReAttrName();
}
