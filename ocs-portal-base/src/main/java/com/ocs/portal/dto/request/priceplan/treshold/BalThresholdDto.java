package com.ocs.portal.dto.request.priceplan.treshold;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BalThresholdDto {
    @Schema(example = "1")
    private Long spId;
    @Schema(example = "1")
    private Integer value;
    @Schema(example = "1")
    private Integer balThresholdId;
    @Schema(example = "1")
    private Integer subBalTypeId;
    private SubBalTypeDto subBalType;
}
