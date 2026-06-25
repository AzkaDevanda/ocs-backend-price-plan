package com.ocs.portal.dto.response.rateplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QryRatePlanZoneAndMappingUnitResponseDto {
    private Integer ratePlanZoneId;
    private Integer ratePlanId;
    private String mappingSrcType;
    private String mappingSrcValue;
    private String mappingDesType;
    private String mappingDesValue;
    private Integer priority;
    private String labelShow;
    private String reAttrName;
    private String zoneMapName;
    private Integer mappingId;
    private String mappingName;
    private String mappingMatchType;
    private String mappingType;
    private String mappingValue;
}
