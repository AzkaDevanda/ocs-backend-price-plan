package com.sts.sinorita.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdvanceTriggerRuleDto {

        @Schema(description = "Trigger rule script and metadata", required = true)
        private TriggerRule triggerRule;

//        @Schema(example = "r.", description = "Function prefix")
//        private String funPrefix;

        @NotNull(message = "effectiveDate cannot be null")
        @Schema(example = "2025-06-30", description = "Effective date in format yyyy-MM-dd")
        private LocalDate effDate;

        @Schema(example = "2025-07-01", description = "Expired date in format yyyy-MM-dd")
        private LocalDate expDate;

        @NotNull(message = "offerVerId cannot be null")
        @Schema(example = "4101", description = "Offer Version ID")
        private Integer offerVerId;

        @NotNull(message = "spId cannot be null")
        @Schema(example = "0", description = "Service Provider ID")
        private int spId;

        @Data
        public static class TriggerRule {

            @Schema(description = "Script to be executed",
                    example = "def main(r):\\n    currentTime= r.event.GetAttrEx(3).AsString()\\n    AcctId = r.event.GetAcctId()\\n    r.event.GetBalValueByAcctId(AcctId,1,currentTime)\\n    BalExpTime = str(r.event.GetBalExpDateValue())\\n    addDays = \\n    newExpDate = r.event.AddDay(currentTime, addDays)\\n    ExTendExpDate = r.event.AddDay(currentTime, addDays+1)\\n    adjustSec = r.event.DiffSeconds(newExpDate,BalExpTime)\\n    r.event.PrintLog(\\\"adjustSec ==\\\",adjustSec )\\n    if adjustSec > 0 :\\n        newExpDate=ExTendExpDate\\n    else :\\n        newExpDate=BalExpTime\\t\\n    r.event.SetAttrList(888, newExpDate)\\n    r.SetResult(0)")
            private String ruleScript;

            @Schema(example = "101", description = "Script template ID")
            private int scriptTempletId;

            @Schema(description = "XML-formatted script page definition",
                    example = "[{\\\"\\\":{\\\"fee\\\":\\\"1\\\",\\\"AcmID\\\":\\\"103\\\"}}]")
            private String scriptPage;
        }
    }





//}
