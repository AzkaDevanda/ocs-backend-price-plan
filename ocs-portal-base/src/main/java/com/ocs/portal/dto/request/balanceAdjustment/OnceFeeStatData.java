package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class OnceFeeStatData {
  private Long basicDeductCharge = Long.valueOf(0L);

  private Long currencyDeductCharge = Long.valueOf(0L);

  private Long basicFirstDeductCharge = Long.valueOf(0L);

  private Long currencyFirstDeductCharge = Long.valueOf(0L);

  private Long basicDiscount = Long.valueOf(0L);

  private Long basicFirstDiscount = Long.valueOf(0L);

  private String refAttr;

  private Long reId;

  private String reName;

  private String acctItemTypeCode;

  private Long acctResId;
}
