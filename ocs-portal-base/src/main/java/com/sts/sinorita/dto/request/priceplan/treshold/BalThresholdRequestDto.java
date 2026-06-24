package com.sts.sinorita.dto.request.priceplan.treshold;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BalThresholdRequestDto {

    @Schema(example = "0", description = "Threshold value")
    private Integer value;

    @Schema(example = "1", description = "Interval for threshold evaluation")
    private Integer interval;

    @Schema(example = "901", description = "Reference attribute ID")
    private Integer reAttr;

    @Schema(example = "Y", description = "Touch PCRF flag (Y/N)")
    private String touchPcrf;

    @Schema(example = "0", description = "Trigger mode for evaluation")
    private String triggerMode;

    @Schema(example = "1547", description = "Trigger ID")
    private Integer triggerId;

    @Schema(example = "0", description = "Service provider ID")
    private Integer spId;

    @Schema(example = "Threshold", description = "Triger by")
    private String triggerBy;

}
