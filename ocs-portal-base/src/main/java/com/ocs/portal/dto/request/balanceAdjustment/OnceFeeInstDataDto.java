package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OnceFeeInstDataDto {
  public Date beginDate;
  public Date endDate;
  public String geoHomeZoneList;
  public String geoHomeZoneCatgList;
  private Long eventInstId;
  private Long priceId;
  private Long seq;
  private Long deductSeq;
  private Long acctId;
  private Long charge;
  private Long acctItemId;
  private Long billingCycleId;
  private String state;
  private Date stateDate;
  private Long subsId;
  private Long oriCharge = Long.valueOf(0L);
  private Long discountCharge = Long.valueOf(0L);
  private Long spId;
  private Long balId;
  private String priceName;
  private Long rateType;
  private Long[] chargeList;
  private Long acctItemTypeId;
  private Long eventChargeAcctItemTypeId;
  private String acctItemTypeName;
  private Long acctResId;
  private String acctResName;
  private Long basicFirstDeductCharge = Long.valueOf(0L);
  private Long currencyFirstDeductCharge = Long.valueOf(0L);
  private Long balShareId;
  private String limitFlag;

  //  private BalanceData balInfo;
  private String refAttr;

  private BalanceShareHisData balShareHisInfo;

  private BalanceShareHisData[] balanceShareHisDataList;
  private String isCurrency;
  private Long basicDeductCharge = Long.valueOf(0L);
  private Long currencyDeductCharge = Long.valueOf(0L);
  private BalanceHisData balHisInfo;
  private Long disctPercent;
  private String cellId;
  private String acctItemTypeCode;

  private Long instalmentTypeId;

  private Long pricePlanId;

//  List<SimpleParamValueData> simpleParamValueDataList;

//  List<TableParamValueData> tableParamValueDataList;

  private String comments;

  private String partyType;

  private String partyCode;

  private Date instalmentDate;

  private String taxFlag;
}
