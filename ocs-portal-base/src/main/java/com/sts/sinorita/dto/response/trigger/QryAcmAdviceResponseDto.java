package com.sts.sinorita.dto.response.trigger;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QryAcmAdviceResponseDto {

    private Integer acmThresholdId;
    private Integer adviceType;
    private String adviceTypeName;
    private Character triggerMode;
    private Integer adviceEventId;
    private String adviceEventName;
    private Integer notifyParamsId;
    private String triggerNotif;


}
