package com.sts.sinorita.dto.response.offerrela;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferRelaAsOriResponseDto {
  private Integer offerRelaId;
  private Integer relaType;
  private Integer destOfferId;
  private Integer oriLowerLimit;
  private Integer oriUpperLimit;
  private Integer destOfferType;
  private Integer destOfferGroupOfferType;
  private String oriOfferName;
  private String destOfferName;
  private String destOfferCode;
  private String destOfferGroupName;
  private String destIndOfferName;
  private String destSubsPlanName;
  private LocalDate destEffDate;
  private LocalDate destExpDate;
}
