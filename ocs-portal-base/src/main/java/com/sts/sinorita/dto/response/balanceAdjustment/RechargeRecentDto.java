package com.sts.sinorita.dto.response.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class RechargeRecentDto {
  public Long acctBookId;

  public String acctBookType;

  public Long acctResId;

  public Long preBalance;

  public Long charge;

  public Date preExpDate;

  public Long seconds;

  public Date createdDate;

  public String tradeSn;

  public Date tradeTime;

  public String tradeMethod;

  public String tradeType;

  public String accountCode;

  public String vcPin;

  public Long amount;

  public Long extendDays;
}
