package com.sts.sinorita.dto.request.priceplan.treshold;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BalAdviceDto {
    @Schema(example = "T")
    private Character triggerAdvice;
    @Schema(example = "80")
    private Integer adviceType;
    @Schema(example = "LOW_NOTIFICATION_50%")
    private String adviceTypeName;
    @Schema(example = "")
    private String adviceEventName;
    @Schema(example = "0")
    private int spId;
    @Schema(example = "null")
    private Integer oldAdviceEventId;
    @Schema(example = "null")
    private Integer oldAdviceType;
    @Schema(example = "23011")
    private String balThresholdId;
}
