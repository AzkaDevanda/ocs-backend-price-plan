package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class BalAdjustDataBus extends BillingBaseDataBus {
  private BalDto balBeforeAdjust;

  private BalDto balAfterAdjust;

  private Long balAdjustAmount;

  private Date balAdjustEffDate;

  private Date balAdjustExpDate;

  private Long balAdjustSeconds;

  private Long balAdjustDays;

  private Date balAdjustPreExpDate;

  private AdjustDto adjustDto;

  private Boolean isPreValidateNotPassed;

  private AdjustDto[] adjustDtoList;

  private BatchAcctBookDto[] batchAcctBookDtoList;

}
