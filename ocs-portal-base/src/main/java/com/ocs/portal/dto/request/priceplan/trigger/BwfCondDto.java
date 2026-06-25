package com.ocs.portal.dto.request.priceplan.trigger;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BwfCondDto {

    @Schema(example = "196")
    private Integer reAttr;

    @Schema(example = "3GPP_RAT_TYPE")
    private String reAttrName;

    @Schema(example = "8")
    private String sortOperator;

    @Schema(example = "<=")
    private String sortOperatorName;

    @Schema(example = "N")
    private String isConst;

    @Schema(example = "1201", nullable = true)
    private Integer rReAttr;

    @Schema(example = "ACCT_CHANGE_STAGE", nullable = true)
    private String rReAttrName;

    @Schema(example = "AdjustTime", nullable = true)
    private String rFunction;

    @Schema(example = "100", nullable = true)
    private String rParam1;

    @Schema(example = "8", nullable = true)
    private String operand;

    @Schema(description = "Sequence number", example = "1")
    private int seq;

    @Schema(description = "Service provider ID", example = "0")
    private int spId;

    @Schema(description = "Function name", example = "AddFeatureToSpecialARIList")
    private String function;

    @Schema(description = "Parameter 1", example = "111")
    private String param1;

    @Schema(description = "Parameter 2", example = "222")
    private String param2;

    @Schema(description = "Reference parameter 2", example = "444")
    private String rParam2;

    @Schema(description = "Zone id", example = "133")
    private Integer zoneId;



}
