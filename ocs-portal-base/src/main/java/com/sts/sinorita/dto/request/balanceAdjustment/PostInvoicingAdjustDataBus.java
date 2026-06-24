package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class PostInvoicingAdjustDataBus extends BillingBaseDataBus {
  private Long postInvoicingAdjustAmount;

  private AcctItemDto[] newAcctItemList;

  private AdjustDto adjustDto;

  private AdjustItemDto[] adjustItemDtoArr;
}
