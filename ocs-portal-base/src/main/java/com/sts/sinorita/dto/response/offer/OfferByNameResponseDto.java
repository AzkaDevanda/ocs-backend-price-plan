package com.sts.sinorita.dto.response.offer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferByNameResponseDto {
  private Integer offerId;
  private String offerName;
  private Integer indepProdSpecId;
}
