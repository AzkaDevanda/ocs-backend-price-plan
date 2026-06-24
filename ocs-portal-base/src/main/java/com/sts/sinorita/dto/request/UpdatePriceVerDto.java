package com.sts.sinorita.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePriceVerDto {
    @Schema(example = "101005")
    private Integer ratePlanId;

    @Schema(example = "null")
    private Integer mappingId;

    @Schema(example = "2025-08-07")
    private LocalDate effectiveDate;

    @Schema(example = "2025-08-08")
    private LocalDate expiredDate;
}
