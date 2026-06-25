package com.ocs.portal.projection.pricePlan.rateplan;

public interface QryRatePlanZoneProjection {
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
}
