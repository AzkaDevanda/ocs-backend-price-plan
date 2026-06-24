package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class ReRecurringId implements java.io.Serializable {

    @Column(name = "RE_ID", nullable = false)
    private Integer reId;

    @Column(name = "OFFER_ID", nullable = false)
    private Integer offerId;

    public Integer getReId() {
        return reId;
    }

    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReRecurringId entity = (ReRecurringId) o;
        return Objects.equals(this.offerId, entity.offerId) &&
                Objects.equals(this.reId, entity.reId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerId, reId);
    }

}