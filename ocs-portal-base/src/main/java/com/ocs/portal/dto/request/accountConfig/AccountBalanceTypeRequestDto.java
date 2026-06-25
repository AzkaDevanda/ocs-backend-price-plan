package com.ocs.portal.dto.request.accountConfig;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceTypeRequestDto {
    private Integer balType;

    private Long parentAcctResId;

    private String acctResName;

    private String isCurrency;

    private String comments;

    private BigDecimal creditLimit;

    private Integer remindDay;

    private BigDecimal remindValue;

    private BigDecimal maxValue;

    private String refillable;

    private String paymentForce;

    private String stdCode;

    private String isFreeUnit;

    private Integer defaultAcctItemTypeId;

    private Integer spId;

    private Integer unitTypeId;

    private BigDecimal unitPrecision;

    private Integer reservePercentage;

//    private UnitTypeDto unitTypeDto;

    private Integer ratioMoney;

    private Integer ratioPrecision;

    private Integer priority;

    @Schema(description = "000000")
    private String extendRule;

    private Integer maxExpDate;

    private BigDecimal maxAdjustValue;

    private BigDecimal maxChgValue;

    private String resetZero;

    private String periodClass;

    private Long storeUnit;

    private String acmType;

    private BigDecimal acmThreshold;

    private String acmUnit;

    private Integer acmAmount;

    private BigDecimal ceilLimit;

    private BigDecimal floorLimit;

    private BigDecimal dailyCeilLimit;

    private BigDecimal dailyFloorLimit;

    private BigDecimal gracePeriod;

    private BigDecimal maxRollover;

    private Integer usageType;

    private String rewardFlag;

    private String unlimitedFlag;

    private Integer adjustType;

    private String overdraftFlag;

    private String balanceAggregation;

    private String category;

    private String rolloverFlag;

    private Integer reservePecentage;

    private String freeFlag;

    private String adjustFlag;

    private String balCategory;

    private String clearFlag;

    private Integer clearDays;

    private String customerFlag;

    AcctResFreeRequestDto acctResFree;

    TransAcctResCfgDtoRequestDto transAcctResCfg;
    
}
