package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class SubsDto {
  private Long defaultPricePlanId;

  private Long subsId;

  private String prefix;

  private String accNbr;

  private Long custId;

  private Long userId;

  private Long agentId;

  private Long orgId;

  private Long areaId;

  private Date updateDate;

  private Long routingId;

  private Long defLangId;

  private String ppsPwd;

  private String comments;

  private Long spId;

  private Long acctId;

  private Long ppsCreditLimit;

  private String needUpload;

  private Long creditLimit;

  private ProdDto prod;

  private String prePayFlag;
}
