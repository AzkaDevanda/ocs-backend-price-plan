package com.sts.sinorita.projection.pricePlan.price;

import java.sql.Blob;
import java.sql.Clob;
import java.time.LocalDate;

public interface QryEventBenefitProjection {
    Integer getPriceId();

    String getPriceName();

    Integer getPriceVerId();

    Integer getSubBalTypeId();

    Integer getPriority();

    Blob getScriptPage();

    String getValue();

    Character getConfigType();

    Integer getReAttr();

    String getReAttrName();

    Integer getRum();

    Integer getCalcPrecision();

    Clob getRuleScript();

    String getRuleComments();

    Integer getScriptTempletId();

    Integer getRepeatCnt();

    Integer getPeriodId();

    Integer getAcctResId();

    Character getIsCurrency();

    String getAcctResName();

    String getOffsetOfEffectiveDateUnit();

    String getDurationOfAvailabilityUnit();

    String getRelEffUnitName();

    String getRelExpUnitName();

    LocalDate getEffectiveDate();

    LocalDate getExpiryDate();

    Character getShareFlag();

    Integer getRatePlanId();

    Integer getRatePlanType();

    Integer getOfferVerId();

    Integer getMappingId();
}
