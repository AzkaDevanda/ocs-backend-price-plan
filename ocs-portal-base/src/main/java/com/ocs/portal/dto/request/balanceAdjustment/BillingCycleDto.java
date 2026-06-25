package com.ocs.portal.dto.request.balanceAdjustment;

import java.util.Date;

public class BillingCycleDto {
  public Long billingCycleId;

  public Long billingCycleTypeId;

  public Date cycleBeginDate;

  public Date cycleEndDate;

  public String state;

  public Date debtDate;

  public Long spId;

  public Date runDate;

  public Date documentDate;
  public Date notificationDate;
  private Date postingDate;
  private Date invoiceDate;
  private Date originDate;
}
