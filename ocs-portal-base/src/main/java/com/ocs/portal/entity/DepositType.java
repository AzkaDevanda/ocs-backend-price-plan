package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Table(name = "DEPOSIT_TYPE")
public class DepositType implements Serializable {
    @Id
    @Column(name = "DEPOSIT_TYPE_ID", nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 60)
    private String name;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "CHARGE")
    private Integer charge;

    @Column(name = "DEPOSIT_TYPE_CODE", length = 30)
    private String depositTypeCode;

    @Column(name = "REFUNDABLE")
    private Character refundable;

    @Column(name = "TRANS_CREDIT")
    private Character transCredit;

    @Column(name = "SP_ID")
    private Integer spId;

    @ColumnDefault("999999")
    @Column(name = "CHECK_DURATION")
    private Integer checkDuration;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public String getDepositTypeCode() {
        return depositTypeCode;
    }

    public void setDepositTypeCode(String depositTypeCode) {
        this.depositTypeCode = depositTypeCode;
    }

    public Character getRefundable() {
        return refundable;
    }

    public void setRefundable(Character refundable) {
        this.refundable = refundable;
    }

    public Character getTransCredit() {
        return transCredit;
    }

    public void setTransCredit(Character transCredit) {
        this.transCredit = transCredit;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Integer getCheckDuration() {
        return checkDuration;
    }

    public void setCheckDuration(Integer checkDuration) {
        this.checkDuration = checkDuration;
    }

}