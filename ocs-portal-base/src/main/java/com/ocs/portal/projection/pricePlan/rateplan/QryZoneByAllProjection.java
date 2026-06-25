package com.ocs.portal.projection.pricePlan.rateplan;

public interface QryZoneByAllProjection {
    Integer getZoneId();

    String getZoneName();

    String getZoneComments();

    String getZoneCode();

    Integer getZoneMapId();

    Integer getParentZoneId();

    String getZoneMapName();

    String getMatchMode();

    String getStdCode();

    String getZoneMapComments();
}
