package com.ocs.portal.dto.request.priceplan.treshold;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TrigerEventAcm {

        @Schema(description = "Subscription Event ID (int)", example = "76")
        private Integer subsEventId;

        @Schema(description = "Trigger Mode (String 1 length)", example = "0")
        private String triggerMode;

        @Schema(description = "Antibill Shock Flag", example = "N")
        private String antibillShock;

        @Schema(description = "Event Name", example = "(Gota) Join Organization")
        private String eventName;

        @Schema(description = "Notification Parameters ID", example = "")
        private String notifyParamsId;

        @Schema(description = "Extra Attributes", example = "")
        private String extAttr;

        @Schema(description = "Service Provider ID", example = "0")
        private int spId;

        @Schema(description = "Old Subscription Event ID", example = "null", nullable = true)
        private String oldSubsEventId;

        @Schema(description = "Threshold ID (Number)", example = "1105")
        private Integer balThresholdId;
    }
