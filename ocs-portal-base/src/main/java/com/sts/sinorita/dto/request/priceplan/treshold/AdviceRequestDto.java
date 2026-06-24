package com.sts.sinorita.dto.request.priceplan.treshold;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdviceRequestDto {

//    @Schema(description = "Trigger advice flag / trigger mode", example = "T")
//    private Character triggerAdvice;
//
//    @Schema(description = "Advice type code", example = "118")
//    private String adviceType;
//
//    @Schema(description = "Trigger mode", example = "0")
//    private Integer triggerMode;
//
//    @Schema(description = "Advice type name", example = "DATA_COST_NOTIFY")
//    private String adviceTypeName;
//
//    @Schema(description = "Advice event name", example = "")
//    private String adviceEventName;
//
//    @Schema(description = "Service Provider ID", example = "0")
//    private Integer spId;
//
//    @Schema(description = "Old advice type code", example = "139")
//    private Integer oldAdviceType;
//
//
//    @NotNull(message = "ACM Threshold ID null")
//    @Schema(description = "ACM Threshold ID", example = "1202")
//    private Integer acmThresholdId;

    private String triggerNotification;
    private Character triggerMode;
    private String notifType;
    private String oldNotifType;
    private Integer oldNotifParamId;
    private Integer oldAdviceEventId;
    private Integer notifParamId;
    private Integer thresholdId;
    private Integer spId;
}
