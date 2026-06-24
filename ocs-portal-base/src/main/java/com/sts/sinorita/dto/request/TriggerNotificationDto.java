package com.sts.sinorita.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TriggerNotificationDto {
    private String triggerNotification;
    private Character triggerMode;
    private String notifType;
    private Integer notifParamId;
    private Integer thresholdId;
    private Integer spId;
}
