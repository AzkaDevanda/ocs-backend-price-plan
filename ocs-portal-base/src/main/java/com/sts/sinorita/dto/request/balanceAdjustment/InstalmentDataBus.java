package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class InstalmentDataBus extends BillingBaseDataBus {
  private Long instalmentAmount;

  private AcctItemDto[] newAcctItemList;

  private InstalmentDto instalmentDto;

  private AcctItemInstalmentDto[] acctItemInstalmentList;
}
