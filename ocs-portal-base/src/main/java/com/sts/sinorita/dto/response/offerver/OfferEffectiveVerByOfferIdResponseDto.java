package com.sts.sinorita.dto.response.offerver;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferEffectiveVerByOfferIdResponseDto {
  private Integer offerVerId;
  private Integer offerId;
  private LocalDateTime effDate;
  private LocalDateTime expDate;
  private Integer seq;
  private Integer spId;
}
