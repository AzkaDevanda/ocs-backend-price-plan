package com.ocs.portal.dto.request;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class InsertReferenceAccumulationDto {
    @Nullable
    private Integer acmTimeSpanId;
    private Long effValue;
    private Long expValue;
    private Integer resourceId;
    private Character calculationMethod;
    private String accumulation;
    private Long calculateUnit;
    private Integer priority;

}
