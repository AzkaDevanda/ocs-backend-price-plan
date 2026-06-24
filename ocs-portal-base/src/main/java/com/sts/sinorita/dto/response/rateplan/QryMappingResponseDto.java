package com.sts.sinorita.dto.response.rateplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QryMappingResponseDto {
    private Integer mappingId;
    private String mappingName;
    private Integer priority;
}
