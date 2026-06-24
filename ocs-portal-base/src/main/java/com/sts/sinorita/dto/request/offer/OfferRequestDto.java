package com.sts.sinorita.dto.request.offer;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferRequestDto {
  @NotNull
  @Schema(description = "")
  private Integer offerId;

  @NotBlank
  @Schema(description = "")
  private Character offerType;

  @NotBlank
  @Schema(description = "")
  private String offerName;

  @Schema(description = "")
  private String comments;

  @Schema(description = "")
  private String offerCode;

  @Schema(description = "")
  private Long saleListPrice;

  @Schema(description = "")
  private Long rentListPrice;

  @Schema(description = "")
  private LocalDate effDate;

  @Schema(description = "")
  private LocalDate expDate;

  @Schema(description = "")
  private LocalDate createdDate;

  @Schema(description = "")
  private Character state;

  @Schema(description = "")
  private LocalDate stateDate;

  @Schema(description = "")
  private String effType;

  @Schema(description = "")
  private String specTime;

  @Schema(description = "")
  private Character autoContinueFlag;

  @Schema(description = "")
  private Integer cycleQuantity;

  @Schema(description = "")
  private Character timeUnit;

  @Schema(description = "")
  private Character duplicateFlag;

  @Schema(description = "")
  private Integer spId;

  @Schema(description = "")
  private Integer expOff;

  @Schema(description = "")
  private Character expTimeUnit;

  @Schema(description = "")
  private Character agreementEffType;

  @Schema(description = "")
  private String salesRuleScript;

  @Schema(description = "")
  private Character salePriceGstType;

  @Schema(description = "")
  private Character rentPriceGstType;

  @Schema(description = "")
  private String prodType;

  @Schema(description = "")
  private Character publishState;

  @Schema(description = "")
  private LocalDate publishStateDate;
}
