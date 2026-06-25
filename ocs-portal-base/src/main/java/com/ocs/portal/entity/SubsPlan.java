package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "SUBS_PLAN")
public class SubsPlan implements Serializable {

    // FK
    // OFFER
    @Id
    @Column(name = "SUBS_PLAN_ID", nullable = false)
    private Integer id;

    @Column(name = "INDEP_PROD_SPEC_ID")
    private Integer indepProdSpecId;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "SALE_FLAG", nullable = false)
    private Character saleFlag;

    @Column(name = "IS_BUNDLE_FLAG")
    private Character isBundleFlag;

    @Column(name = "SP_ID")
    private Integer spId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndepProdSpecId() {
        return indepProdSpecId;
    }

    public void setIndepProdSpecId(Integer indepProdSpecId) {
        this.indepProdSpecId = indepProdSpecId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Character getSaleFlag() {
        return saleFlag;
    }

    public void setSaleFlag(Character saleFlag) {
        this.saleFlag = saleFlag;
    }

    public Character getIsBundleFlag() {
        return isBundleFlag;
    }

    public void setIsBundleFlag(Character isBundleFlag) {
        this.isBundleFlag = isBundleFlag;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}