package com.sts.sinorita.dto.response.trigger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QryAcmThresholdResponseDto {
    private Integer acmThresholdId;

    private Integer triggerId;

    private Long value;

    private Long interval;

    private Integer reAttr;

    private String reAttrName;

    private Integer ratio;

    private Character touchPcrf;

    private String triggerMode;

    private Long acmBilShockRuleId;

    private String ruleName;

    private Integer offAttr;
    
    private String triggerBy;

    private Integer onOffAttr;
}
