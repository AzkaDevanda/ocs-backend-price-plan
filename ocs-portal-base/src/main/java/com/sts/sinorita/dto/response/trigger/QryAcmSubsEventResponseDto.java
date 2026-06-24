package com.sts.sinorita.dto.response.trigger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QryAcmSubsEventResponseDto {
    private Integer acmThresholdId;

    private Integer subsEventId;

    private String eventName;

    private String extAttr;

    private Character triggerMode;

    private Character antibillShock;

    private Integer notifyParamsId;
}
