package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class DisctObjAitidId implements java.io.Serializable {

    @Column(name = "DISCT_OBJ_ID", nullable = false)
    private Integer disctObjId;

    @Column(name = "ACCT_ITEM_TYPE_ID", nullable = false)
    private Integer acctItemTypeId;

    public Integer getDisctObjId() {
        return disctObjId;
    }

    public void setDisctObjId(Integer disctObjId) {
        this.disctObjId = disctObjId;
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
        DisctObjAitidId entity = (DisctObjAitidId) o;
        return Objects.equals(this.acctItemTypeId, entity.acctItemTypeId) &&
                Objects.equals(this.disctObjId, entity.disctObjId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acctItemTypeId, disctObjId);
    }

}