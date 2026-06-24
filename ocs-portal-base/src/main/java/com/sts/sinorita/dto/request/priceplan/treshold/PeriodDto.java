package com.sts.sinorita.dto.request.priceplan.treshold;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PeriodDto {

    @Schema(example = "2025-04-17")
    @NotNull(message = "absEffDate cannot be null")
    private LocalDate absEffDate;

    @Schema(example = "2025-04-17")
    @NotNull(message = "absExpDate cannot be null")
    private LocalDate absExpDate;

    @NotNull(message = "periodId cannot be null")
    private Integer periodId;
}
