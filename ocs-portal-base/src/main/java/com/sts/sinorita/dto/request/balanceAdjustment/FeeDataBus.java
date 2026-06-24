package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class FeeDataBus extends BillingBaseDataBus {
  private List<ReCcInstDataDto> reCcInstDataList;

  private EventPaymentData eventPaymentData;

  private List<AcctDto> acctList;
}
