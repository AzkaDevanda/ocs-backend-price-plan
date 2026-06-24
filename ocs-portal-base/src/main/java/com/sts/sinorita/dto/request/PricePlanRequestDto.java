package com.sts.sinorita.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sts.sinorita.dto.request.priceplan.VersionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PricePlanRequestDto {

    @NotNull(message = "offerType cannot be null")
    @Schema(example = "4")
    private String offerType;

    @NotNull(message = "offerName cannot be null")
    @Schema(example = "Testing triger 1055")
    private String offerName;

    @NotNull(message = "pricePlanType cannot be null")
    @Schema(example = "3")
    private Character pricePlanType;

    @Schema(example = "S")
    private String  applyLevel;

    @Schema(example = "TTD")
    private String pricePlanCode;

    @Schema(example = "testing")
    private String remarks;

//    private String copyFrom,sourceFrom, effType;

    private Integer spId, priority,serviceType;

//    private Long offerId;

   @NotNull(message = "baseValidPeriod / EF Date cannot be null")
   @Schema(example = "2030-10-10")
   private LocalDate baseValidPeriod;

    @Schema(example = "2030-10-10")
    private LocalDate expBaseValidPeriod;

    private VersionDto version;

//    @Schema(example = "2030-10-10")
//    private LocalDate versionExpPeriod;

//   @Schema(example = "2030-09-06")
//   private LocalDate versionValidPeriod;

//   @Schema(example = "offerVerId from copyFrom")
//   private Integer offerVerId;
}

