package com.sts.sinorita.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricePlanUpdateDto {
    @NotNull(message = "offerType cannot be null")
    @Schema(example = "4")
    private String offerType;

    @NotNull(message = "offerName cannot be null")
    @Schema(example = "Testing triger 1055")
    private String offerName;

//    @NotNull(message = "pricePlanType cannot be null")
//    @Schema(example = "3")
//    private Character pricePlanType;

    @Schema(example = "TTD")
    private String pricePlanCode;

    @Schema(example = "3")
    private Character pricePlanType;

    @NotNull(message = "baseValidPeriod / EF Date cannot be null")
    @Schema(example = "2030-10-10")
    private LocalDate baseValidPeriod;

    @Schema(example = "2030-10-10")
    private LocalDate expBaseValidPeriod;

    private Integer serviceType;

    @Schema(example = "testing")
    private String remarks;

    private String applyLevel;
}
