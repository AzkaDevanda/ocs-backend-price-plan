package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class SubsUppInstDto {
  private Long subsUppInstId;

  private Long subsId;

  private Long pricePlanId;

  private Date createdDate;

  private Date effDate;

  private Date expDate;

  private Date updateDate;

  private String state;

  private Date stateDate;

  private Long prodId;

  private Long parentSubsUppInstId;

  private Date completedDate;

  private String needUpload;

  private String packageFlag;

  private Date agmEffDate;

  private Date agmExpDate;

  private Long spId;

  private Long routingId;

  private String uploadType;

  private String prodAlias;

  private String parentOfferId;

  private String agreementTimeUnit;

  private Long agreementLimit;

  public SubsUppInstDto (Long pricePlanId, Date effDate, Date expDate) {
    this.pricePlanId = pricePlanId;
    this.effDate = effDate;
    this.expDate = expDate;
  }
}
