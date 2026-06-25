package com.ocs.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalSubsEventDto {
    private Long subsEventId;
    private Long balThresholdId;
    private String eventName;
    private String refAttr;
    private Character triggerMode;
    private Character antibillShock;
    private String extAttr;
    private Long notifyParamsId;
}
