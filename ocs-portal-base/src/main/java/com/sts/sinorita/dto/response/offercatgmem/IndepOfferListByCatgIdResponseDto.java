package com.sts.sinorita.dto.response.offercatgmem;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndepOfferListByCatgIdResponseDto {
  private Integer offerCatgMemId;
  private Integer childOfferCatgId;
  private Integer seq;
  private Integer offerId;
  private Integer offerType;
  private String offerName;
  private String offerCode;
  private LocalDate effDate;
  private LocalDate expDate;
  private String effType;
  private String expOff;
  private String timeUnit;
  private String comments;
  private String prodType;
  private Integer indepProdSpecId;
  private Integer servType;
  private String paidFlag;
  private Integer brandPricePlanId;
  private String servTypeName;
  private Integer servTypePaidFlag;
  private String networkType;
}
