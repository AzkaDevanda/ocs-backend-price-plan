package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "ACCT_ITEM_GROUP")
public class AcctItemGroup implements Serializable {
    @Id
    @Column(name = "ACCT_ITEM_GROUP_ID", nullable = false)
    private Integer id;

    @Column(name = "ACCT_ITEM_GROUP_NAME", nullable = false, length = 60)
    private String acctItemGroupName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "ACCT_ITEM_GROUP_CODE", length = 30)
    private String acctItemGroupCode;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "ZERO_FEE_PRINT_FLAG")
    private Boolean zeroFeePrintFlag;

    @Column(name = "BILL_PRIORITY")
    private Integer billPriority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAcctItemGroupName() {
        return acctItemGroupName;
    }

    public void setAcctItemGroupName(String acctItemGroupName) {
        this.acctItemGroupName = acctItemGroupName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAcctItemGroupCode() {
        return acctItemGroupCode;
    }

    public void setAcctItemGroupCode(String acctItemGroupCode) {
        this.acctItemGroupCode = acctItemGroupCode;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Boolean getZeroFeePrintFlag() {
        return zeroFeePrintFlag;
    }

    public void setZeroFeePrintFlag(Boolean zeroFeePrintFlag) {
        this.zeroFeePrintFlag = zeroFeePrintFlag;
    }

    public Integer getBillPriority() {
        return billPriority;
    }

    public void setBillPriority(Integer billPriority) {
        this.billPriority = billPriority;
    }

}