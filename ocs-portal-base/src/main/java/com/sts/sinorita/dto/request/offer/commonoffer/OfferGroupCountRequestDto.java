package com.sts.sinorita.dto.request.offer.commonoffer;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferGroupCountRequestDto {
  @Parameter(description = "")
  @Schema(example = "6")
  private Integer offerGroupType;

  @Parameter(description = "", required = false)
  @Schema(example = "0")
  private Integer shareFlag;

  @Parameter(description = "", required = false)
  @Schema(example = "4")
  private Integer indepProdSpecId;

  @Parameter(description = "", required = false)
  @Schema(example = "1503")
  private Integer offerVerId;

  @Parameter(description = "", required = false)
  @Schema(example = "505")
  private Integer offerGroupId;

  @Parameter(description = "")
  @Schema(example = "0")
  private Integer spId;
}