package com.sts.sinorita.dto.request.offer;

import lombok.Data;

import java.util.Date;

@Data
public class QryAllPricePlanSpecResponseDto {
  private Long pricePlanId;
  private String applyLevel;
  private Integer priority;
  private String pricePlanName;
  private String offerCode;
  private Long saleListPrice;
  private Long rentListPrice;
  private Date effDate;
  private Date expDate;
  private Date createdDate;
  private String state;
  private Date stateDate;
  private String effType;
  private String autoContinueFlag;
  private Integer cycleQuantity;
  private String timeUnit;
  private String duplicateFlag;
  private String comments;
}
