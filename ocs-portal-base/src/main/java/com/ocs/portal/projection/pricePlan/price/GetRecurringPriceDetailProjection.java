package com.ocs.portal.projection.pricePlan.price;

import java.sql.Blob;
import java.time.LocalDate;

public interface GetRecurringPriceDetailProjection {
    Integer getPriceId();

    String getPriceName();

    LocalDate getEffDate();

    LocalDate getExpDate();

    String getValueString();

    Integer getAcctItemTypeId();

    String getAcctItemTypeName();

    Long getCalculateUnit();

    String getComments();

    Character getRatePrecision();

    Character getCalcPrecision();

    Integer getCreditLimit();

    Integer getPriority();

    String getPayIndicator();

    Integer getScriptTempletId();

    String getRuleScript();

    String getRuleComments();

    String getParam();

    byte[] getScriptPage();

    Character getConfigType();

}
