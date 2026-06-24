package com.sts.sinorita.dto.request.priceplan.treshold;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SubBalTypeDto {
    private String value;
    private String acctResId;
    private String spanGroup;
    @Schema(example = "2025-04-17")
    @NotNull(message = "absEffDate cannot be null")
    private LocalDate absEffDate;
    @Schema(example = "2025-04-17")
    @NotNull(message = "absExpDate cannot be null")
    private LocalDate absExpDate;
    private String acctResName;
    private List<SubBalTypeLimitDto> subBalTypeLimitList;
    private String subBalTypeLimitNames;
    private Double ceilLimit;
    private Double dailyCeilLimit;
    private PeriodDto period;
    @NotNull(message = "SubBalType.periodId cannot be null")
    private Integer periodId;
    private Integer priority;
    @NotNull(message = "subBalTypeId cannot be null")
    private Integer subBalTypeId;
}
