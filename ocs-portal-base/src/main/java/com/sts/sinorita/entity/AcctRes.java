package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCT_RES", schema = "STS")
@SequenceGenerator(name = "acct_seq_generator", sequenceName = "ACCT_RES_ID_SEQ", allocationSize = 1)
public class AcctRes {

    @Id
    @Column(name = "ACCT_RES_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acct_seq_generator")
    private Long acctResId;

    @Column(name = "BAL_TYPE")
    private Integer balType;

    @Column(name = "PARENT_ACCT_RES_ID")
    private Long parentAcctResId;

    @Column(name = "ACCT_RES_NAME")
    private String acctResName;

    @Column(name = "IS_CURRENCY")
    private Character isCurrency;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "CREDIT_LIMIT")
    private BigDecimal creditLimit;

    @Column(name = "REMIND_DAY")
    private Integer remindDay;

    @Column(name = "REMIND_VALUE")
    private BigDecimal remindValue;

    @Column(name = "MAX_VALUE")
    private BigDecimal maxValue;

    @Column(name = "REFILLABLE")
    private Character refillable;

    @Column(name = "PAYMENT_FORCE")
    private Character paymentForce;

    @Column(name = "STD_CODE")
    private String stdCode;

    @Column(name = "IS_FREE_UNIT")
    private Character isFreeUnit;

    @Column(name = "DEFAULT_ACCT_ITEM_TYPE_ID")
    private Integer defaultAcctItemTypeId;

    @Column(name = "UNIT_TYPE_ID")
    private Integer unitTypeId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "UNIT_PRECISION")
    private BigDecimal unitPrecision;

    @Column(name = "RATIO_MONEY")
    private Integer ratioMoney;

    @Column(name = "RATIO_PRECISION")
    private Integer ratioPrecision;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "EXTEND_RULE", length = 6)
    private Character extendRule;

    @Column(name = "MAX_ADJUST_VALUE")
    private BigDecimal maxAdjustValue;

    @Column(name = "MAX_EXP_DATE")
    private Integer maxExpDate;

    @Column(name = "MAX_CHG_VALUE")
    private BigDecimal maxChgValue;

    @Column(name = "RESET_ZERO")
    private Character resetZero;

    @Column(name = "PERIOD_CLASS")
    private String periodClass;

    @Column(name = "STORE_UNIT")
    private Integer storeUnit;

    @Column(name = "ACM_THRESHOLD")
    private BigDecimal acmThreshold;

    @Column(name = "ACM_TYPE")
    private String acmType;

    @Column(name = "ACM_UNIT")
    private Character acmUnit;

    @Column(name = "ACM_AMOUNT")
    private Integer acmAmount;

    @Column(name = "CEIL_LIMIT")
    private BigDecimal ceilLimit;

    @Column(name = "FLOOR_LIMIT")
    private BigDecimal floorLimit;

    @Column(name = "DAILY_CEIL_LIMIT")
    private BigDecimal dailyCeilLimit;

    @Column(name = "DAILY_FLOOR_LIMIT")
    private BigDecimal dailyFloorLimit;

    @Column(name = "OVERDRAFT_FLAG")
    private Character overdraftFlag;

    @Column(name = "BALANCE_AGGREGATION")
    private Character balanceAggregation;

    @Column(name = "ROLLOVER_FLAG")
    private Character rolloverFlag;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "RESERVE_PECENTAGE")
    private Integer reservePercentage;

    @Column(name = "BAL_CATEGORY")
    private Character balCategory;

    @Column(name = "GRACE_PERIOD")
    private BigDecimal gracePeriod;

    @Column(name = "MAX_ROLLOVER")
    private BigDecimal maxRollover;

    @Column(name = "USAGE_TYPE")
    private Integer usageType;

    @Column(name = "REWARD_FLAG")
    private Character rewardFlag;

    @Column(name = "UNLIMITED_FLAG")
    private Character unlimitedFlag;

    @Column(name = "ADJUST_TYPE")
    private Integer adjustType;

    @Column(name = "FREE_FLAG")
    private String freeFlag;

    @Column(name = "ADJUST_FLAG")
    private Character adjustFlag;

    @Column(name = "CLEAR_FLAG")
    private Character clearFlag;

    @Column(name = "CLEAR_DAYS")
    private Integer clearDays;

    @Column(name = "CUST_LEVEL")
    private Character customerFlag;
}

