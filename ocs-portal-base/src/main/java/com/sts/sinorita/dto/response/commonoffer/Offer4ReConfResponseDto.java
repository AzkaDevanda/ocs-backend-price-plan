package com.sts.sinorita.dto.response.commonoffer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Offer4ReConfResponseDto {
  private Integer prodSpecId;
  private String prodSpecName;
  private String stdCode;
  private Character offerType;
  private String offerTypeName;
  private Integer offerId;
  private String offerName;
}
