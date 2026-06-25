package com.ocs.portal.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccumulationTriggerDto {

    @NotNull(message = "effectiveDate cannot be null")
    @Schema(example = "2025-04-23T00:00:00", description = "Tanggal mulai efektif")
    private LocalDateTime effectiveDate;// Format: yyyy-MM-dd HH:mm:ss

    @Schema(example = "2025-04-23T00:00:00", description = "Tanggal mulai efektif")
    private LocalDateTime expiryDate;    // Format: yyyy-MM-dd

    @Schema(example = "2", description = "resource id")
    private Integer accumulationType; // contoh: Daily Consume Amount / resource id

    @Schema(example = "1", description = "triger type")
    private Character triggerMode;      // contoh: Increment

    @Schema(example = "1", description = "Offer Ver ID")
    private Integer offerVerId;

    @Schema(example = "1", description = "Destination, ex. CVBS, MCCM, BOTH")
    private Character destination;      // bisa kosong atau pilih dari dropdown

    @Schema(example = "2025-04-23T00:00:00", description = "State date")
    private LocalDateTime stateDate;
    private String thresholdDetail;  // link atau keterangan threshold (Detail)
}
