package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class GlCodeCfgDto {
  private Long glCodeCfgId;

  private Long priority;

  private String subsEventId;

  private String acctBookType;

  private String paymentMethodId;

  private String loanType;

  private String depositTypeId;

  private String acctResId;

  private String reasonId;

  private String acctItemTypeId;

  private String glDirection;

  private Long glCoefficient;

  private String glCode;

  private Long spId;

  private String ledgerType;
}
