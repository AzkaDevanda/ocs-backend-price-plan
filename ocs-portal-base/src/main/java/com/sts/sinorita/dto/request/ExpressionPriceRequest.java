package com.sts.sinorita.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionPriceRequest {

    @Schema(description = "ID dari script templet", example = "201")
    private Integer scriptTempletId;

    @Schema(example = "[{\\\"\\\":{\\\"fee\\\":\\\"1\\\",\\\"AcmID\\\":\\\"103\\\"}}]")
    private String jsonScriptPage;

    @Schema(description = "ID dari time span", example = "def main(r):\\n    ##mainBal = r.event.GetBalByAcctResId(1)\\n    mainBal = r.event.GetBalByVirAcctResID(202)\\n    mainBal = - mainBal\\n    if ( mainBal >= 6000 and mainBal < 8000):\\n\\t    charge = 6000\\n    else:\\n\\t    charge = 8000\\n    r.SetResult(charge)")
    @NotNull(message = "ruleScript cannot be null")
    private String ruleScript;

    @Schema(example = "null")
    private String ruleComment;

}
