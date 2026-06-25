package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class RePricePlanId implements java.io.Serializable {

    @Column(name = "RE_ID", nullable = false)
    private Integer reId;

    @Column(name = "OFFER_VER_ID", nullable = false)
    private Integer offerVerId;

    public Integer getReId() {
        return reId;
    }

    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public Integer getOfferVerId() {
        return offerVerId;
    }

    public void setOfferVerId(Integer offerVerId) {
        this.offerVerId = offerVerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RePricePlanId entity = (RePricePlanId) o;
        return Objects.equals(this.reId, entity.reId) &&
                Objects.equals(this.offerVerId, entity.offerVerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reId, offerVerId);
    }

}