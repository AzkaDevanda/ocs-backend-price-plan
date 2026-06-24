package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class DisputeDataBus extends BillingBaseDataBus {
  private Long disputeAmount;

  private Long disputeProcessAmount;

  private DisputeDto disputeDto;
}
