package com.ocs.portal.projection.pricePlan.rateplan;

public interface QryScriptTemplateProjection {
    Long getScriptTempletId();

    String getScriptTempletName();

    String getComments();

    String getScriptTempletGroup();

    String getTempletContent();

    String getTemplateFlag();

    Long getSrcScriptTemplateId();
}
