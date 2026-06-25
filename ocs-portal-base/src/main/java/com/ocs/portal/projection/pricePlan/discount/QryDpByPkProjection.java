package com.ocs.portal.projection.pricePlan.discount;

import java.sql.Blob;
import java.sql.Clob;

public interface QryDpByPkProjection {
    Integer getDpId();
    Integer getPricePlanVerId();
    String getDpType();
    String getDpName();
    String getComments();
    Integer getPriority();
    String getDpTypeName();
    Integer getDpRuleAcctItemTypeId();
    String getDpRuleAcctItemTypeName();
    Clob getRuleScript();
    String getRuleComments();
    Blob getScriptPage();
    Integer getScriptTempletId();
    String getCalcType();
    String getValue();
    Integer getResultAcctItemTypeId();
    String getCalcTypeName();
    String getResultAcctItemTypeName();
    String getIsCurrency();
    Character getBillingPlanType();
}
