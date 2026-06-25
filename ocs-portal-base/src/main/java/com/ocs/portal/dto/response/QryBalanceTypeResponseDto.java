package com.ocs.portal.dto.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class QryBalanceTypeResponseDto {
  private String balType;
  private String balTypeName;
  private Long acctResId;
  private String acctResName;
  private String isCurrency;
  private String comments;
  private BigDecimal creditLimit;
  private BigDecimal maxValue;
  private String refillable;
  private String stdCode;
  private String paymentForce;
  private Long parentAcctResId;
  private String isFreeUnit;
  private Long defaultAcctItemTypeId;
  private String parentAcctResName;
  private String acctItemTypeName;
  private Long unitTypeId;
  private Integer unitPrecision;
  private String unitTypeName;
  private BigDecimal ratioMoney;
  private Integer ratioPrecision;
  private Integer priority;
  private String extendRule;
  private String extendRuleName;
  private BigDecimal maxAdjustValue;
  private BigDecimal maxChgValue;
  private Integer maxExpDate;
  private String resetZero;
  private Integer gracePeriod;
  private Integer maxRollover;
  private Integer dayThreshold;
  private Integer weekThreshold;
  private Integer monthThreshold;
  private Integer dayCount;
  private Integer weekCount;
  private Integer monthCount;
  private BigDecimal minResidualBal;
  private BigDecimal maxAllowed;
  private BigDecimal minAllowed;
  private BigDecimal transferFactor;
  private String resetZeroName;
}