package com.ocs.portal.dto.request.offer.commonoffer;

import com.ocs.portal.dto.request.common.PagingRequestDto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Offer4ReConfRequestDto extends PagingRequestDto {

  @Parameter(description = "")
  @Schema(example = "MusicaTL Daily subscription")
  private String prodSpecName;

  @Parameter(description = "")
  @Schema(example = "MUSICA1D")
  private String stdCode;

  @Parameter(description = "")
  @Schema(example = "4")
  private Character offerType;

  @Parameter(description = "")
  @Schema(example = "A")
  private Character state;

  @Parameter(description = "")
  @Schema(example = "0")
  private Integer spId;

}
