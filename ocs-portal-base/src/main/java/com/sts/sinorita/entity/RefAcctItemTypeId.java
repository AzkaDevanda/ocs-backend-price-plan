package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class RefAcctItemTypeId implements java.io.Serializable {

    @Column(name = "DP_ID", nullable = false)
    private Integer dpId;

    @Column(name = "ACCT_ITEM_TYPE_ID", nullable = false)
    private Integer acctItemTypeId;

    public Integer getDpId() {
        return dpId;
    }

    public void setDpId(Integer dpId) {
        this.dpId = dpId;
    }

    public Integer getAcctItemTypeId() {
        return acctItemTypeId;
    }

    public void setAcctItemTypeId(Integer acctItemTypeId) {
        this.acctItemTypeId = acctItemTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RefAcctItemTypeId entity = (RefAcctItemTypeId) o;
        return Objects.equals(this.acctItemTypeId, entity.acctItemTypeId) &&
                Objects.equals(this.dpId, entity.dpId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acctItemTypeId, dpId);
    }

}