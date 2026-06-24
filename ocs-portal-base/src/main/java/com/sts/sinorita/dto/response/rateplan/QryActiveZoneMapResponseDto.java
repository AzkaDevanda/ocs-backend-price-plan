package com.sts.sinorita.dto.response.rateplan;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QryActiveZoneMapResponseDto {
    private Integer zoneMapId;
    private String zoneMapName;
    private String comments;
    private String matchMode;
    private String stdCode;
    private String zoneMapType;
    private Integer glTypeId;
    private String state;
}
