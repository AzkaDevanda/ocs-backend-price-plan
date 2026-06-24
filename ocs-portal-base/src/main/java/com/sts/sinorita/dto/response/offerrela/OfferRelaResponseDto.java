package com.sts.sinorita.dto.response.offerrela;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferRelaResponseDto {
  private String relaType;
  private Integer oriLowerLimit;
  private Integer oriUpperLimit;
  private String oriOfferType;
  private String oriOfferGroupOfferType;
  private String destOfferType;
  private String destOfferGroupOfferType;
  private String oriOfferName;
  private String oriOfferGroupName;
  private String destOfferName;
  private String destOfferGroupName;
  private String oriIndOfferName;
  private String oriSubsPlanName;
  private LocalDateTime oriEffDate;
  private LocalDateTime oriExpDate;
  private String destIndOfferName;
  private String destSubsPlanName;
  private LocalDateTime destEffDate;
  private LocalDateTime destExpDate;
}