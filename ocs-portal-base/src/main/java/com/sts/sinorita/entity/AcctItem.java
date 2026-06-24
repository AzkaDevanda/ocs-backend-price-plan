package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "ACCT_ITEM")
public class AcctItem implements Serializable {
    @Id
    @Column(name = "ACCT_ITEM_ID", nullable = false)
    private Long id;

    @Column(name = "BILLING_CYCLE_ID", nullable = false)
    private Integer billingCycleId;

    @Column(name = "ACCT_ID", nullable = false)
    private Long acctId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "CHARGE", nullable = false)
    private Long charge;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDate createdDate;

    @Column(name = "STATE", nullable = false)
    private Boolean state = false;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "BAL_ID")
    private Long balId;

    @Column(name = "SETTLE_CHARGE")
    private Long settleCharge;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "ACCT_BOOK_ID")
    private Long acctBookId;

    @Column(name = "ACCT_ITEM_TYPE_ID")
    private Integer acctItemTypeId;

    @Column(name = "BASIC_CHARGE")
    private Long basicCharge;

    @ColumnDefault("0")
    @Column(name = "DISCT_PERCENT")
    private Integer disctPercent;

    @ColumnDefault("1")
    @Column(name = "GST_SEQ")
    private Short gstSeq;

    @ColumnDefault("0")
    @Column(name = "RATE_USAGE")
    private Long rateUsage;

    @ColumnDefault("0")
    @Column(name = "REAL_USAGE")
    private Long realUsage;

    @ColumnDefault("9999")
    @Column(name = "SRC_APP_ID")
    private Integer srcAppId;

    @ColumnDefault("'0'")
    @Column(name = "BILLING_PLAN_TYPE")
    private Boolean billingPlanType;

    @ColumnDefault("0")
    @Column(name = "BATCH_ID")
    private Long batchId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBillingCycleId() {
        return billingCycleId;
    }

    public void setBillingCycleId(Integer billingCycleId) {
        this.billingCycleId = billingCycleId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public Long getSubsId() {
        return subsId;
    }

    public void setSubsId(Long subsId) {
        this.subsId = subsId;
    }

    public Long getCharge() {
        return charge;
    }

    public void setCharge(Long charge) {
        this.charge = charge;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public LocalDate getStateDate() {
        return stateDate;
    }

    public void setStateDate(LocalDate stateDate) {
        this.stateDate = stateDate;
    }

    public Long getBalId() {
        return balId;
    }

    public void setBalId(Long balId) {
        this.balId = balId;
    }

    public Long getSettleCharge() {
        return settleCharge;
    }

    public void setSettleCharge(Long settleCharge) {
        this.settleCharge = settleCharge;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Long getAcctBookId() {
        return acctBookId;
    }

    public void setAcctBookId(Long acctBookId) {
        this.acctBookId = acctBookId;
    }

    public Integer getAcctItemTypeId() {
        return acctItemTypeId;
    }

    public void setAcctItemTypeId(Integer acctItemTypeId) {
        this.acctItemTypeId = acctItemTypeId;
    }

    public Long getBasicCharge() {
        return basicCharge;
    }

    public void setBasicCharge(Long basicCharge) {
        this.basicCharge = basicCharge;
    }

    public Integer getDisctPercent() {
        return disctPercent;
    }

    public void setDisctPercent(Integer disctPercent) {
        this.disctPercent = disctPercent;
    }

    public Short getGstSeq() {
        return gstSeq;
    }

    public void setGstSeq(Short gstSeq) {
        this.gstSeq = gstSeq;
    }

    public Long getRateUsage() {
        return rateUsage;
    }

    public void setRateUsage(Long rateUsage) {
        this.rateUsage = rateUsage;
    }

    public Long getRealUsage() {
        return realUsage;
    }

    public void setRealUsage(Long realUsage) {
        this.realUsage = realUsage;
    }

    public Integer getSrcAppId() {
        return srcAppId;
    }

    public void setSrcAppId(Integer srcAppId) {
        this.srcAppId = srcAppId;
    }

    public Boolean getBillingPlanType() {
        return billingPlanType;
    }

    public void setBillingPlanType(Boolean billingPlanType) {
        this.billingPlanType = billingPlanType;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

}