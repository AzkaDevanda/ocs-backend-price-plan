package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class PresentFeeInstData {
  public String geoHomeZoneList;
  public String geoHomeZoneCatgList;
  public String prodAttrList;
  private Long eventInstId;
  private Long acctBookId;
  private Long priceId;
  private Long subBalTypeId;
  private Date effDate;
  private Date expDate;
  private Long presentBal = Long.valueOf(0L);
  private Long rateType;
  private String priceName;
  private Long acctResId;
  private String acctResName;
  private Long presentSeconds = Long.valueOf(0L);
  private String refAttr;
  private String isCurrency;
  private Long basicPresentCharge = Long.valueOf(0L);
  private Long basicPresentSeconds = Long.valueOf(0L);
  private Long currencyPresentCharge = Long.valueOf(0L);
  private Long currencyPresentSeconds = Long.valueOf(0L);
  private Long spId;
  private BalanceData balanceInfo;
  private Long seq;
  private String state;
  private Date stateDate;
  private Date createdDate;
  private Long effSeconds = Long.valueOf(0L);
  private Long basicEffSeconds = Long.valueOf(0L);
  private Long currencyEffSeconds = Long.valueOf(0L);
  private String cellId;
  private String presentIntervalUnit;
  private Long presentIntervalTime;
  private Long benefitRevenue;
  private Long balId;
  private Long presentAcctId;

  private Long presentSubsId;

  private String subBenefitFlag;
}
