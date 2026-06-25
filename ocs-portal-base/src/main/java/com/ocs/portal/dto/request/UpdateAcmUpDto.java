package com.ocs.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAcmUpDto {
    private Integer timeSpanUpId;
    private Long rangeEffVal;
    private Long rangeExpVal;
    private Integer resourceId;
    private Character adjustMethod;
    private Integer up;
    private Long calUnit;

}
