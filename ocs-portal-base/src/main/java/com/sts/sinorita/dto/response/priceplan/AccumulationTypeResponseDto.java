package com.sts.sinorita.dto.response.priceplan;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccumulationTypeResponseDto {
    private Integer resourceId;
    private String resourceName;
    private Integer reAttrId;
    private String reAttrName;
}
