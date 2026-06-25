package com.ocs.portal.dto.request;

import com.ocs.portal.dto.request.offer.OfferRequestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DependProdSpecRequestDto {
  @Schema(description = "")
  private Integer dependProdSpecId;

  private OfferRequestDto offer;

  @Schema(description = "", defaultValue = "138")
  private Integer servType;

  @Schema(description = "", defaultValue = "N | Y")
  private String isPackage;

  @Schema(description = "", defaultValue = "0")
  private Integer spId;

  @Schema(description = "")
  private Integer lifecycleType;

  @Schema(description = "")
  private Integer depositTypeId;

  @Schema(description = "")
  private String operFlag;

  @Schema(description = "")
  private Integer offerCatgId;

  @Schema(description = "")
  private Integer offerCatgMemId;

  @Schema(description = "", defaultValue = "G")
  private String networkType;

  @Schema(description = "")
  private String groupType;

  @Schema(description = "")
  private Integer upperLimit;

  @Schema(description = "")
  private Integer lowerLimit;
}
