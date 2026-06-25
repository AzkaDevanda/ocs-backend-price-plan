package com.ocs.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTriggerNotificationDto {
    private Integer notificationType;
    private Integer notificationEvent;
    private Character triggerMode;
    private Integer notifyParamsId;
}
