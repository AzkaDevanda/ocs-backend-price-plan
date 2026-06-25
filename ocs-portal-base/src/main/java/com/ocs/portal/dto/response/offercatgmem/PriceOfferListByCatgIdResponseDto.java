package com.ocs.portal.dto.response.offercatgmem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceOfferListByCatgIdResponseDto {
  private String seq;
  private String offerCatgMemId;
  private String offerId;
  private String offerType;
  private String offerName;
  private String offerCode;
  private String effDate;
  private String expDate;
  private String duplicateFlag;
  private String expOff;
  private String expTimeUnit;
  private String isPackage;
  private String applyLevel;
  private String policyFlag;
  private String warnLevel;
  private String pricePlanType;
}
