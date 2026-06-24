package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalDeductData {
  public String geoHomeZoneList;
  public String geoHomeZoneCatgList;
  private Long acctId;
  private Long acctBookId;
  private Long balId;
  private Long balDeductAcctResId;
  private String balDeductAcctResName;
  private Long balDeductCharge;
  private Date effDate;
  private Date expDate;
  private AcctBookDto deductAcctBookDto;
  private Long eventPaymentId;
  private Long effSeconds = Long.valueOf(0L);
  private String cellId;
  private String acctItemTypeCode;

  private String refAttr;

  private Long eventInstId;

  private Long priceId;

  private Long seq;

  private Long deductSeq;

  private String forceFlag;

  private BalanceHisData balHisInfo;

  private Long preBalance;

  private Long aftBalance;
}
