package com.sts.sinorita.dto.response.balanceAdjustment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AcctResDto {
  private Long acctResId;
  private Integer balType;
  private Long parentAcctResId;
  private String acctResName;
  private Character isCurrency;
  private String comments;
  private BigDecimal creditLimit;
  private Integer remindDay;
  private BigDecimal remindValue;
  private Long maxValue;
  private Character refillable;
  private Character paymentForce;
  private String stdCode;
  private Character isFreeUnit;
  private Integer defaultAcctItemTypeId;
  private Integer unitTypeId;
  private Integer spId;
  private BigDecimal unitPrecision;
  private Integer ratioMoney;
  private Integer ratioPrecision;
  private Integer priority;
  private String extendRule;
  private Long maxAdjustValue;
  private Long maxExpDate;
  private Long maxChgValue;
  private Character resetZero;
  private String periodClass;
  private Integer storeUnit;
  private BigDecimal acmThreshold;
  private String acmType;
  private Character acmUnit;
  private Integer acmAmount;
  private Long ceilLimit;
  private Long floorLimit;
  private Long dailyCeilLimit;
  private Long dailyFloorLimit;
  private Character overdraftFlag;
  private Character balanceAggregation;
  private Character rolloverFlag;
  private String category;
  private Integer reservePercentage;
  private Character balCategory;
  private Long gracePeriod;
  private Long maxRollover;
  private Integer usageType;
  private Character rewardFlag;
  private Character unlimitedFlag;
  private Integer adjustType;
  private String freeFlag;
  private Character adjustFlag;
  private Character clearFlag;
  private Integer clearDays;
  private Character customerFlag;
}
