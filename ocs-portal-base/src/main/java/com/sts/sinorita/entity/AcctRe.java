package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Table(name = "ACCT_RES")
public class AcctRe implements Serializable {

    @Id
    @Column(name = "ACCT_RES_ID", nullable = false)
    private Integer id;

    @Column(name = "BAL_TYPE", nullable = false)
    private Short balType;

    @Column(name = "PARENT_ACCT_RES_ID")
    private Integer parentAcctResId;

    @Column(name = "ACCT_RES_NAME", nullable = false, length = 60)
    private String acctResName;

    @Column(name = "IS_CURRENCY", nullable = false)
    private Character isCurrency;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "CREDIT_LIMIT")
    private Long creditLimit;

    @Column(name = "REMIND_DAY")
    private Integer remindDay;

    @Column(name = "REMIND_VALUE")
    private Long remindValue;

    @Column(name = "MAX_VALUE")
    private Long maxValue;

    @Column(name = "REFILLABLE")
    private Character refillable;

    @Column(name = "PAYMENT_FORCE")
    private Character paymentForce;

    @Column(name = "STD_CODE", length = 60)
    private String stdCode;

    @Column(name = "IS_FREE_UNIT")
    private Character isFreeUnit;

    @Column(name = "DEFAULT_ACCT_ITEM_TYPE_ID")
    private Integer defaultAcctItemTypeId;

    @Column(name = "UNIT_TYPE_ID")
    private Short unitTypeId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "UNIT_PRECISION")
    private Long unitPrecision;

    @Column(name = "RATIO_MONEY")
    private Integer ratioMoney;

    @Column(name = "RATIO_PRECISION")
    private Integer ratioPrecision;

    @Column(name = "PRIORITY")
    private Integer priority;

    @ColumnDefault("'000000'")
    @Column(name = "EXTEND_RULE", length = 6)
    private String extendRule;

    @Column(name = "MAX_ADJUST_VALUE")
    private Long maxAdjustValue;

    @Column(name = "MAX_EXP_DATE")
    private Integer maxExpDate;

    @Column(name = "MAX_CHG_VALUE")
    private Long maxChgValue;

    @Column(name = "RESET_ZERO")
    private Character resetZero;

    @Column(name = "PERIOD_CLASS", length = 60)
    private String periodClass;

    @Column(name = "STORE_UNIT")
    private Short storeUnit;

    @Column(name = "ACM_THRESHOLD")
    private Long acmThreshold;

    @Column(name = "ACM_TYPE", length = 30)
    private String acmType;

    @Column(name = "ACM_UNIT")
    private Character acmUnit;

    @Column(name = "ACM_AMOUNT")
    private Integer acmAmount;

    @Column(name = "CEIL_LIMIT")
    private Long ceilLimit;

    @Column(name = "FLOOR_LIMIT")
    private Long floorLimit;

    @Column(name = "DAILY_CEIL_LIMIT")
    private Long dailyCeilLimit;

    @Column(name = "DAILY_FLOOR_LIMIT")
    private Long dailyFloorLimit;

    @Column(name = "OVERDRAFT_FLAG")
    private Character overdraftFlag;

    @Column(name = "BALANCE_AGGREGATION")
    private Character balanceAggregation;

    @Column(name = "ROLLOVER_FLAG")
    private Character rolloverFlag;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "RESERVE_PECENTAGE")
    private Short reservePecentage;

    @Column(name = "BAL_CATEGORY")
    private Character balCategory;

    @Column(name = "GRACE_PERIOD")
    private Long gracePeriod;

    @Column(name = "MAX_ROLLOVER")
    private Long maxRollover;

    @Column(name = "USAGE_TYPE")
    private Short usageType;

    @ColumnDefault("'N'")
    @Column(name = "REWARD_FLAG")
    private Character rewardFlag;

    @ColumnDefault("'N'")
    @Column(name = "UNLIMITED_FLAG")
    private Character unlimitedFlag;

    @ColumnDefault("7")
    @Column(name = "ADJUST_TYPE")
    private Short adjustType;

    @Column(name = "FREE_FLAG")
    private Character freeFlag;

    @Column(name = "ADJUST_FLAG")
    private Character adjustFlag;

    @ColumnDefault("'N'")
    @Column(name = "CLEAR_FLAG")
    private Character clearFlag;

    @Column(name = "CLEAR_DAYS")
    private Short clearDays;

    @Column(name = "CUST_LEVEL")
    private Character custLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getBalType() {
        return balType;
    }

    public void setBalType(Short balType) {
        this.balType = balType;
    }

    public Integer getParentAcctResId() {
        return parentAcctResId;
    }

    public void setParentAcctResId(Integer parentAcctResId) {
        this.parentAcctResId = parentAcctResId;
    }

    public String getAcctResName() {
        return acctResName;
    }

    public void setAcctResName(String acctResName) {
        this.acctResName = acctResName;
    }

    public Character getIsCurrency() {
        return isCurrency;
    }

    public void setIsCurrency(Character isCurrency) {
        this.isCurrency = isCurrency;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Long creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Integer getRemindDay() {
        return remindDay;
    }

    public void setRemindDay(Integer remindDay) {
        this.remindDay = remindDay;
    }

    public Long getRemindValue() {
        return remindValue;
    }

    public void setRemindValue(Long remindValue) {
        this.remindValue = remindValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public Character getRefillable() {
        return refillable;
    }

    public void setRefillable(Character refillable) {
        this.refillable = refillable;
    }

    public Character getPaymentForce() {
        return paymentForce;
    }

    public void setPaymentForce(Character paymentForce) {
        this.paymentForce = paymentForce;
    }

    public String getStdCode() {
        return stdCode;
    }

    public void setStdCode(String stdCode) {
        this.stdCode = stdCode;
    }

    public Character getIsFreeUnit() {
        return isFreeUnit;
    }

    public void setIsFreeUnit(Character isFreeUnit) {
        this.isFreeUnit = isFreeUnit;
    }

    public Integer getDefaultAcctItemTypeId() {
        return defaultAcctItemTypeId;
    }

    public void setDefaultAcctItemTypeId(Integer defaultAcctItemTypeId) {
        this.defaultAcctItemTypeId = defaultAcctItemTypeId;
    }

    public Short getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Short unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Long getUnitPrecision() {
        return unitPrecision;
    }

    public void setUnitPrecision(Long unitPrecision) {
        this.unitPrecision = unitPrecision;
    }

    public Integer getRatioMoney() {
        return ratioMoney;
    }

    public void setRatioMoney(Integer ratioMoney) {
        this.ratioMoney = ratioMoney;
    }

    public Integer getRatioPrecision() {
        return ratioPrecision;
    }

    public void setRatioPrecision(Integer ratioPrecision) {
        this.ratioPrecision = ratioPrecision;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getExtendRule() {
        return extendRule;
    }

    public void setExtendRule(String extendRule) {
        this.extendRule = extendRule;
    }

    public Long getMaxAdjustValue() {
        return maxAdjustValue;
    }

    public void setMaxAdjustValue(Long maxAdjustValue) {
        this.maxAdjustValue = maxAdjustValue;
    }

    public Integer getMaxExpDate() {
        return maxExpDate;
    }

    public void setMaxExpDate(Integer maxExpDate) {
        this.maxExpDate = maxExpDate;
    }

    public Long getMaxChgValue() {
        return maxChgValue;
    }

    public void setMaxChgValue(Long maxChgValue) {
        this.maxChgValue = maxChgValue;
    }

    public Character getResetZero() {
        return resetZero;
    }

    public void setResetZero(Character resetZero) {
        this.resetZero = resetZero;
    }

    public String getPeriodClass() {
        return periodClass;
    }

    public void setPeriodClass(String periodClass) {
        this.periodClass = periodClass;
    }

    public Short getStoreUnit() {
        return storeUnit;
    }

    public void setStoreUnit(Short storeUnit) {
        this.storeUnit = storeUnit;
    }

    public Long getAcmThreshold() {
        return acmThreshold;
    }

    public void setAcmThreshold(Long acmThreshold) {
        this.acmThreshold = acmThreshold;
    }

    public String getAcmType() {
        return acmType;
    }

    public void setAcmType(String acmType) {
        this.acmType = acmType;
    }

    public Character getAcmUnit() {
        return acmUnit;
    }

    public void setAcmUnit(Character acmUnit) {
        this.acmUnit = acmUnit;
    }

    public Integer getAcmAmount() {
        return acmAmount;
    }

    public void setAcmAmount(Integer acmAmount) {
        this.acmAmount = acmAmount;
    }

    public Long getCeilLimit() {
        return ceilLimit;
    }

    public void setCeilLimit(Long ceilLimit) {
        this.ceilLimit = ceilLimit;
    }

    public Long getFloorLimit() {
        return floorLimit;
    }

    public void setFloorLimit(Long floorLimit) {
        this.floorLimit = floorLimit;
    }

    public Long getDailyCeilLimit() {
        return dailyCeilLimit;
    }

    public void setDailyCeilLimit(Long dailyCeilLimit) {
        this.dailyCeilLimit = dailyCeilLimit;
    }

    public Long getDailyFloorLimit() {
        return dailyFloorLimit;
    }

    public void setDailyFloorLimit(Long dailyFloorLimit) {
        this.dailyFloorLimit = dailyFloorLimit;
    }

    public Character getOverdraftFlag() {
        return overdraftFlag;
    }

    public void setOverdraftFlag(Character overdraftFlag) {
        this.overdraftFlag = overdraftFlag;
    }

    public Character getBalanceAggregation() {
        return balanceAggregation;
    }

    public void setBalanceAggregation(Character balanceAggregation) {
        this.balanceAggregation = balanceAggregation;
    }

    public Character getRolloverFlag() {
        return rolloverFlag;
    }

    public void setRolloverFlag(Character rolloverFlag) {
        this.rolloverFlag = rolloverFlag;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Short getReservePecentage() {
        return reservePecentage;
    }

    public void setReservePecentage(Short reservePecentage) {
        this.reservePecentage = reservePecentage;
    }

    public Character getBalCategory() {
        return balCategory;
    }

    public void setBalCategory(Character balCategory) {
        this.balCategory = balCategory;
    }

    public Long getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Long gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Long getMaxRollover() {
        return maxRollover;
    }

    public void setMaxRollover(Long maxRollover) {
        this.maxRollover = maxRollover;
    }

    public Short getUsageType() {
        return usageType;
    }

    public void setUsageType(Short usageType) {
        this.usageType = usageType;
    }

    public Character getRewardFlag() {
        return rewardFlag;
    }

    public void setRewardFlag(Character rewardFlag) {
        this.rewardFlag = rewardFlag;
    }

    public Character getUnlimitedFlag() {
        return unlimitedFlag;
    }

    public void setUnlimitedFlag(Character unlimitedFlag) {
        this.unlimitedFlag = unlimitedFlag;
    }

    public Short getAdjustType() {
        return adjustType;
    }

    public void setAdjustType(Short adjustType) {
        this.adjustType = adjustType;
    }

    public Character getFreeFlag() {
        return freeFlag;
    }

    public void setFreeFlag(Character freeFlag) {
        this.freeFlag = freeFlag;
    }

    public Character getAdjustFlag() {
        return adjustFlag;
    }

    public void setAdjustFlag(Character adjustFlag) {
        this.adjustFlag = adjustFlag;
    }

    public Character getClearFlag() {
        return clearFlag;
    }

    public void setClearFlag(Character clearFlag) {
        this.clearFlag = clearFlag;
    }

    public Short getClearDays() {
        return clearDays;
    }

    public void setClearDays(Short clearDays) {
        this.clearDays = clearDays;
    }

    public Character getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(Character custLevel) {
        this.custLevel = custLevel;
    }

}