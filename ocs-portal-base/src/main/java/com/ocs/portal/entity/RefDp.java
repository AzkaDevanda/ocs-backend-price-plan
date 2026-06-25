package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "REF_DP")
public class RefDp implements Serializable {
    @Id
    @Column(name = "DP_ID", nullable = false)
    private Integer id;

    @Column(name = "CALC_TYPE")
    private Boolean calcType;

    @Column(name = "VALUE", nullable = false)
    private Integer value;

    @Column(name = "RESULT_ACCT_ITEM_TYPE_ID")
    private Integer resultAcctItemTypeId;

    @Column(name = "SP_ID")
    private Integer spId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getCalcType() {
        return calcType;
    }

    public void setCalcType(Boolean calcType) {
        this.calcType = calcType;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getResultAcctItemTypeId() {
        return resultAcctItemTypeId;
    }

    public void setResultAcctItemTypeId(Integer resultAcctItemTypeId) {
        this.resultAcctItemTypeId = resultAcctItemTypeId;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}