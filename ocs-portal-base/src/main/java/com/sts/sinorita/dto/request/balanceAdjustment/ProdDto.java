package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class ProdDto {
  private Long prodId;

  private String offerCode;

  private Long offerId;

  private Date completedDate;

  private String prodState;

  private String prodStdState;

  private Long subsPlanId;

  private String subsPlanCode;

  private String packageFlag;

  private Long parentProdId;

  private Long indepProdId;

  private Date prodStateDate;

  private Date updateDate;

  private Date createdDate;

  private String state;

  private Date stateDate;

  private String blockReason;

  private Date prodExpDate;

  private String needUpload;

  private Date agreementExpDate;

  private Long spId;

  private Date agreementEffDate;

  private Long agreementLimit;

  private String agreementTimeUnit;

  private Long routingId;

  private Date activeDate;
}
