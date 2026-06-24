package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RePpRecurringId implements java.io.Serializable {

    @Column(name = "RE_ID", nullable = false)
    private Integer reId;

    @Column(name = "PRICE_PLAN_ID", nullable = false)
    private Integer pricePlanId;

    public Integer getReId() {
        return reId;
    }

    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public Integer getPricePlanId() {
        return pricePlanId;
    }

    public void setPricePlanId(Integer pricePlanId) {
        this.pricePlanId = pricePlanId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RePpRecurringId entity = (RePpRecurringId) o;
        return Objects.equals(this.reId, entity.reId) &&
                Objects.equals(this.pricePlanId, entity.pricePlanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reId, pricePlanId);
    }

}