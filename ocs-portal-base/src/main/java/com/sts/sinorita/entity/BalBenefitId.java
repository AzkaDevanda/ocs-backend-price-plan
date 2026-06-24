package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class BalBenefitId implements java.io.Serializable {

    @Column(name = "BAL_THRESHOLD_ID", nullable = false)
    private Integer balThresholdId;

    @Column(name = "SUB_BAL_TYPE_ID", nullable = false)
    private Integer subBalTypeId;

    public Integer getBalThresholdId() {
        return balThresholdId;
    }

    public void setBalThresholdId(Integer balThresholdId) {
        this.balThresholdId = balThresholdId;
    }

    public Integer getSubBalTypeId() {
        return subBalTypeId;
    }

    public void setSubBalTypeId(Integer subBalTypeId) {
        this.subBalTypeId = subBalTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BalBenefitId entity = (BalBenefitId) o;
        return Objects.equals(this.balThresholdId, entity.balThresholdId) &&
                Objects.equals(this.subBalTypeId, entity.subBalTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balThresholdId, subBalTypeId);
    }

}