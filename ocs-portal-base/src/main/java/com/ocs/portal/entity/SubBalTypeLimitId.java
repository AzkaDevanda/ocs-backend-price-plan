package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Setter
@Getter
@Embeddable
public class SubBalTypeLimitId implements java.io.Serializable {

    @Column(name = "SUB_BAL_TYPE_ID", nullable = false)
    private Integer subBalTypeId;

    @Column(name = "ACCT_ITEM_TYPE_ID", nullable = false)
    private Integer acctItemTypeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SubBalTypeLimitId entity = (SubBalTypeLimitId) o;
        return Objects.equals(this.acctItemTypeId, entity.acctItemTypeId) &&
                Objects.equals(this.subBalTypeId, entity.subBalTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acctItemTypeId, subBalTypeId);
    }

}