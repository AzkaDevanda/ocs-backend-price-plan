package com.sts.sinorita.dto.response.offerver;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferVerByOfferIdResponseDto {
  private Integer offerVerId;
  private Integer offerId;
  private LocalDateTime expDate;
  private LocalDateTime effDate;
  private String name;
  private String type;
}
