package com.sts.sinorita.dto.request.priceplan.trigger;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BwfActionDto {


    @Schema(description = "Source attribute ID", example = "1201")
    private Integer srcReAttr;

    @Schema(description = "Source attribute name", example = "ACCT_CHANGE_STAGE")
    private String srcReAttrName;

    @Schema(description = "Object attribute ID", example = "1201")
    private Integer objReAttr;

    @Schema(description = "Object attribute name", example = "ACCT_CHANGE_STAGE")
    private String objReAttrName;

    @Schema(description = "Function to apply", example = "AddFeatureToSpecialARIList")
    private String function;

    @Schema(description = "First parameter", example = "777")
    private String param1;

    @Schema(description = "Second parameter", example = "888")
    private String param2;

    @Schema(description = "Sequence number", example = "1")
    private Integer seq;

    @Schema(description = "Service provider ID", example = "0")
    private Integer spId;
}
