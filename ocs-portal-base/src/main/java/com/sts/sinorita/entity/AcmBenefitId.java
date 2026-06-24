package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AcmBenefitId implements Serializable {

    @Column(name = "ACM_THRESHOLD_ID", nullable = false)
    private Integer acmThresholdId;

    @Column(name = "SUB_BAL_TYPE_ID", nullable = false)
    private Integer subBalTypeId;

    public Integer getAcmThresholdId() {
        return acmThresholdId;
    }

    public void setAcmThresholdId(Integer acmThresholdId) {
        this.acmThresholdId = acmThresholdId;
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
        AcmBenefitId entity = (AcmBenefitId) o;
        return Objects.equals(this.acmThresholdId, entity.acmThresholdId) &&
                Objects.equals(this.subBalTypeId, entity.subBalTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acmThresholdId, subBalTypeId);
    }

}