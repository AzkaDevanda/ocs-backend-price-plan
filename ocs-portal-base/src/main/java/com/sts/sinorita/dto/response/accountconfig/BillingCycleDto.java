package com.sts.sinorita.dto.response.accountconfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingCycleDto {
  private Integer billingCycleId;
  private Integer billingCycleTypeId;
  private Character state;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date cycleBeginDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date cycleEndDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date debtDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date runDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date originDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date documentDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date postingDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date invoiceDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date NotificationDate;

  private Integer spId;
}
