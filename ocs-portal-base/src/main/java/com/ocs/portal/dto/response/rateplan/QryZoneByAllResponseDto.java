package com.ocs.portal.dto.response.rateplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QryZoneByAllResponseDto {
    private Integer zoneId;
    private String zoneName;
    private String zoneComments;
    private String zoneCode;
    private Integer zoneMapId;
    private Integer parentZoneId;
    private String zoneMapName;
    private String matchMode;
    private String stdCode;
    private String zoneMapComments;
}
