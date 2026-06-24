package com.sts.sinorita.dto.request.offer.offerCatg;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RootCatgRequestDto {

  @Parameter(description = "")
  @Schema(example = "2")
  private Integer offerCatgType;

  @Parameter(description = "")
  @Schema(example = "A")
  private Character offerCatgClass;

  @Parameter(description = "")
  @Schema(example = "1")
  private Integer offerCatgId;

  @Parameter(description = "")
  @Schema(example = "")
  private String offerCatgName;

  @Parameter(description = "")
  @Schema(example = "0")
  private Integer spId;

  @Parameter(description = "")
  @Schema(example = "N")
  private Character policyFlag;
}
