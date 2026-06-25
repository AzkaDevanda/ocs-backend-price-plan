package com.ocs.portal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvanceBenefitRequestDto {
    // ADVANCED BENEFIT
    private Integer scriptTempletId;

    @Schema(example = "[{\\\"\\\":{\\\"fee\\\":\\\"1\\\",\\\"AcmID\\\":\\\"103\\\"}}]")
    private String jsonScriptPage;

    @Schema(description = "ID dari time span", example = "def main(r): \n" +
            "    addDays = 30\n" +
            "    benefit = -5368709120\n" +
            "    CurrentTime = r.event.GetAttrEx(3).AsString()\n" +
            "    newExpDate = r.event.AddSeconds(CurrentTime, addDays * 86400 -1 )\n" +
            "    r.event.SetAttrList(888,newExpDate)\n" +
            "    r.event.SetAttrList(42,benefit)\n" +
            "    r.SetResult(0)")
    @NotNull(message = "ruleScript cannot be null")
    private String ruleScript;

    @Schema(example = "null")
    private String advancedBenefitRemarks;
}
