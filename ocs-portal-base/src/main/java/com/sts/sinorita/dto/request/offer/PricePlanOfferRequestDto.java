package com.sts.sinorita.dto.request.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricePlanOfferRequestDto {
  public Integer pricePlanId;

  public Character applyLevel;

  public Integer servType;

  public Integer priority;

  public Character isPackage;

  public Integer spId;

  public String policyFlag;

  public OfferRequestDto offer;

  @NotBlank
  @Schema(description = "")
  public Character pricePlanType;

  public String operFlag;

  public Integer offerCatgId;

  public Integer offerCatgMemId;

  public Integer seq;

  public Character groupType;

  public Integer upperLimit;

  public Integer lowerLimit;
}
