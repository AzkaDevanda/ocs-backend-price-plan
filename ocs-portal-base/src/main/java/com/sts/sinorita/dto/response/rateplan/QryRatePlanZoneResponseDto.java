package com.sts.sinorita.dto.response.rateplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QryRatePlanZoneResponseDto {
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
}
