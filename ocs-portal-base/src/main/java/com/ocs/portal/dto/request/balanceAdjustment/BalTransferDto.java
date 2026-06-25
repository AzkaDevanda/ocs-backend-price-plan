package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class BalTransferDto {
  public Long acctBookId;

  public Long inAcctBookId;

  public Long spId;

  public Long outSubsId;

  public String outPrefix;

  public String outAccNbr;

  public Long inSubsId;

  public String inPrefix;

  public String inAccNbr;

  public Long reversedAbId;

  public Long reversedByAbIid;

  public Long reversedInAbId;

  public Long reversedByInAbId;

  public String comments;

  public String refAttr;

  public Long transferReasonId;
}
