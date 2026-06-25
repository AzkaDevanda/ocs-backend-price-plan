package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Data
@SequenceGenerator(name = "acct_item_type_id_seq", sequenceName = "STS.ACCT_ITEM_TYPE_ID_SEQ", allocationSize = 1)
@Table(name = "ACCT_ITEM_TYPE")
public class AcctItemType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acct_item_type_id_seq")
    @Column(name = "ACCT_ITEM_TYPE_ID", nullable = false)
    private Integer id;

    @Column(name = "GST_TYPE")
    private Character gstType;

    @Column(name = "ACCT_RES_ID", nullable = false)
    private Integer acctResId;

    @Column(name = "PARENT_ID")
    private Integer parentId;

    @Column(name = "EXCHANGE_ITEM_TYPE_ID")
    private Integer exchangeItemTypeId;

    @Column(name = "ACCT_ITEM_TYPE_NAME", nullable = false, length = 60)
    private String acctItemTypeName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "ACCT_ITEM_TYPE_CODE", length = 30)
    private String acctItemTypeCode;

    @Column(name = "USAGE_TYPE")
    private Character usageType;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "ZERO_FEE_PRINT_FLAG")
    private Character zeroFeePrintFlag;

    @ColumnDefault("100")
    @Column(name = "BILL_PRIORITY")
    private Integer billPriority;

    @Column(name = "DEFAULT_TAX_ITEM_TYPE_ID")
    private Integer defaultTaxItemTypeId;

    @Column(name = "FEE_CLASS")
    private Character feeClass;

    @Column(name = "ACCT_ITEM_GROUP_ID")
    private Integer acctItemGroupId;

    @Column(name = "FEE_TYPE")
    private Character feeType;

    @Column(name = "BILL_ITEM_TYPE", length = 30)
    private String billItemType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Character getGstType() {
        return gstType;
    }

    public void setGstType(Character gstType) {
        this.gstType = gstType;
    }

    public Integer getAcctResId() {
        return acctResId;
    }

    public void setAcctResId(Integer acctResId) {
        this.acctResId = acctResId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getExchangeItemTypeId() {
        return exchangeItemTypeId;
    }

    public void setExchangeItemTypeId(Integer exchangeItemTypeId) {
        this.exchangeItemTypeId = exchangeItemTypeId;
    }

    public String getAcctItemTypeName() {
        return acctItemTypeName;
    }

    public void setAcctItemTypeName(String acctItemTypeName) {
        this.acctItemTypeName = acctItemTypeName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAcctItemTypeCode() {
        return acctItemTypeCode;
    }

    public void setAcctItemTypeCode(String acctItemTypeCode) {
        this.acctItemTypeCode = acctItemTypeCode;
    }

    public Character getUsageType() {
        return usageType;
    }

    public void setUsageType(Character usageType) {
        this.usageType = usageType;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Character getZeroFeePrintFlag() {
        return zeroFeePrintFlag;
    }

    public void setZeroFeePrintFlag(Character zeroFeePrintFlag) {
        this.zeroFeePrintFlag = zeroFeePrintFlag;
    }

    public Integer getBillPriority() {
        return billPriority;
    }

    public void setBillPriority(Integer billPriority) {
        this.billPriority = billPriority;
    }

    public Integer getDefaultTaxItemTypeId() {
        return defaultTaxItemTypeId;
    }

    public void setDefaultTaxItemTypeId(Integer defaultTaxItemTypeId) {
        this.defaultTaxItemTypeId = defaultTaxItemTypeId;
    }

    public Character getFeeClass() {
        return feeClass;
    }

    public void setFeeClass(Character feeClass) {
        this.feeClass = feeClass;
    }

    public Integer getAcctItemGroupId() {
        return acctItemGroupId;
    }

    public void setAcctItemGroupId(Integer acctItemGroupId) {
        this.acctItemGroupId = acctItemGroupId;
    }

    public Character getFeeType() {
        return feeType;
    }

    public void setFeeType(Character feeType) {
        this.feeType = feeType;
    }

    public String getBillItemType() {
        return billItemType;
    }

    public void setBillItemType(String billItemType) {
        this.billItemType = billItemType;
    }

}