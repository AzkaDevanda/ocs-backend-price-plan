package com.sts.sinorita.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMappingUnitRequest {
    private Integer ratePlanZoneId;
    private Character mappingType;
    private Character mappingMatchType;
    private String mappingValue;
}
