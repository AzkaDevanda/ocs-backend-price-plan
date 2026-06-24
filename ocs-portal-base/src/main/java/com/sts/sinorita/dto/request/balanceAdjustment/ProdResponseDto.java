package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProdResponseDto {
  private List<ProdAttrValueResponseDto> prodAttrValueList;

  private Long prodId;

  private Long offerId;

  private Date completedDate;

  private String prodState;

  private Date activeDate;

  private Long subsPlanId;

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

  private Date agreementEffDate;

  private Long agreementLimit;

  private String agreementTimeUnit;

  private Long spId;

  private Long routingId;

  private String uploadType;

  // TODO : REFACTOR ENTITY JADI DTO
//  private Offer offer;

  private String prodAlias;


}
