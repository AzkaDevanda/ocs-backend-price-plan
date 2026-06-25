package com.ocs.portal.projection.pricePlan.price;

import java.time.LocalDate;

public interface PriceInfoProjection {
    Long getPriceId();
    String getPriceName();
    String getPayIndicator();
    Integer getPriceAcctItemTypeId();
    String getValue();
    Character getCalcPrecision();
    Long getRum();
    Integer getReAttr();
    String getReAttrName();
    String getComments();
    Integer getPriceVerId();
    Integer getCalcDisAitId();
    Integer getParentPriceId();
    Integer getMappingId();
    LocalDate getEffDate();
    LocalDate getExpDate();
    String getAcctItemTypeName();
    Integer getAcctItemTypeId();
    String getAcctResName();
    Character getIsCurrency();
    Long getCreditLimit();
    Integer getPriority();
    Character getRatePrecision();
    Integer getDepositTypeId();
    String getDepositTypeName();
    String getParam();
    Character getShareFlag();
    Integer getRatePlanId();
    Character getRatePlanType();
    Integer getOfferVerId();
    Integer getAcctItemTypeIdParam();
    String getValueString();
}
