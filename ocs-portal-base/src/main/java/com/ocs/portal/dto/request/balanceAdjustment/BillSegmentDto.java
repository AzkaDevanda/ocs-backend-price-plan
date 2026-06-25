package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class BillSegmentDto extends BillDto {
  private Long billingCycleSeq;

  private Long postRealBalance;

  private String state;

  private Date stateDate;

  private Long batchId;
}
