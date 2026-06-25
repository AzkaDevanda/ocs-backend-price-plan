package com.ocs.portal.projection;

public interface ZoneProjection {
    Long getZoneId();
    String getZoneName();
    String getZoneComments();
    String getZoneCode();
    Long getZoneMapId();
    Long getParentZoneId();
    String getZoneMapName();
    String getMatchMode();
    String getStdCode();
    String getZoneMapComments();
    String getMatchModeName();
}
