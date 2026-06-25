package com.ocs.portal.dto.request;

import lombok.Data;

@Data
public class InsertTimeSpanAccumulationDto {
    private Integer timeSpanId;
    private Character calculationMethod;
    private String valueString;
    private Long calculationUnit;
    private Integer priority;


}
