package com.ocs.portal.dto.response.trigger;

import lombok.Data;

@Data
public class TriggerThresholdDto {
    private Integer tresholdId;
    private Integer triggerId;
    private Double value;
    private Integer interval;
    private Integer reAttr;
    private String reAttrName;
    private String ratio;
    private String touchPcrf;
    private String triggerMode;
    private String triggerBy;

}
