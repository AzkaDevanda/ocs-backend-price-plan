package com.sts.sinorita.dto.request.priceplan.treshold;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdviceDelReqDto {
    @NotNull(message = "ACM Threshold ID tidak boleh null")
    @Schema(description = "ID Threshold untuk ACM", example = "1202")
    private Integer acmThresholdId;

    @Schema(description = "ID Event Advice (boleh null)", example = "EVENT_001")
    private Integer adviceEventId;

    @NotNull(message = "Advice Type tidak boleh null")
    @Schema(description = "Tipe Advice", example = "118")
    private Integer adviceType;

    @Schema(description = "ID Notify Params (boleh null)", example = "NOTIFY_456")
    private Integer notifyParamsId;

    @NotNull(message = "SP ID tidak boleh null")
    @Schema(description = "Service Provider ID", example = "0")
    private Integer spId;
}
