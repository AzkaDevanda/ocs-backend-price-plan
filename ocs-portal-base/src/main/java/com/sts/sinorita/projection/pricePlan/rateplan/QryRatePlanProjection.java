package com.sts.sinorita.projection.pricePlan.rateplan;

public interface QryRatePlanProjection {
    Integer getRatePlanId();

    String getRatePlanName();

    Integer getPriority();

    String getRatePlanType();

    String getRatePlanCode();

    String getMappingExit();

    Character getRatePlanMapping();

    Integer getOfferVerId();

    Integer getReId();

    String getTemplateFlag();

    String getRemarks();
}
