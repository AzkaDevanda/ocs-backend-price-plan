package com.sts.sinorita.dto.response.priceplan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BalanceTresholdResponseDto {
    private Integer tresholdId;
    private Integer triggerId;
    private Double value;
    private Integer interval;
    private Integer reAttr;
    private String reAttrName;
    private String ratio;
    private Long touchPcrf;
    private String triggerMode;
}
