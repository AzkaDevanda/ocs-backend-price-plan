package com.sts.sinorita.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OfferDto {
  private Long offerId;

  private String offerType;

  private String offerName;

  private String comments;

  private String offerCode;

  private Long saleListPrice;

  private Long rentListPrice;

  private LocalDateTime effDate;

  private LocalDateTime expDate;

  private LocalDateTime createdDate;

  private String state;

  private LocalDateTime stateDate;

  private String effType;

  private String specTime;

  private String autoContinueFlag;

  private Long cycleQuantity;

  private String timeUnit;

  private String duplicateFlag;

  private Long spId;

  private Long expOff;

  private String expTimeUnit;

  private String agreementEffType;

  private String salePriceGstType;

  private String rentPriceGstType;

  private String prodType;
}
