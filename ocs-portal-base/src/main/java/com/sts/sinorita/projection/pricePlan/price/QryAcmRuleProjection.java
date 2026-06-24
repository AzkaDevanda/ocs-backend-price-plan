package com.sts.sinorita.projection.pricePlan.price;

import java.sql.Blob;
import java.sql.Clob;

public interface QryAcmRuleProjection {

    Integer getAcmRuleId();

    Clob getRuleScript();

    Integer getPriority();

    String getRuleComments();

    Integer getPriceVerId();

    Integer getScriptTempletId();

    Blob getScriptPage();

}
