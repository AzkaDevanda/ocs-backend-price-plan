package com.ocs.portal.projection.pricePlan.price;

import java.sql.Blob;
import java.sql.Clob;

public interface QryUpRuleProjection {
    Integer getUpRuleId();
    Long getUpId();
    Integer getPriority();
    Clob getRuleScript();
    String getRuleComments();
    Blob getScriptPage();
    Long getScriptTempletId();
}
