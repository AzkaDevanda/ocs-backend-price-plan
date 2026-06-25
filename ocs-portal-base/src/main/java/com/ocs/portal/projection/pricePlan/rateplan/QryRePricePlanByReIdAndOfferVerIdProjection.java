package com.ocs.portal.projection.pricePlan.rateplan;

import java.sql.Blob;
import java.sql.Clob;

public interface QryRePricePlanByReIdAndOfferVerIdProjection {
    Long getReId();

    Long getOfferVerId();

    Long getSpId();

    Clob getRuleScript();

    Blob getScriptPage();

    Long getScriptTempletId();

    Long getWorkflowId();
}
