package com.sts.sinorita.projection.pricePlan.rateplan;

public interface QryRatePlanZoneAndMappingUnitProjection {
    Integer getRatePlanZoneId();

    Integer getRatePlanId();

    String getMappingSrcType();

    String getMappingSrcValue();

    String getMappingDesType();

    String getMappingDesValue();

    Integer getPriority();

    String getLabelShow();

    String getReAttrName();

    String getZoneMapName();

    Integer getMappingId();

    String getMappingName();

    String getMappingMatchType();

    String getMappingType();

    String getMappingValue();
}
