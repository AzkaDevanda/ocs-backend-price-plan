package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class RefValueToOfferVerId implements java.io.Serializable {

    @Column(name = "REF_VALUE_ID", nullable = false)
    private Integer refValueId;

    @Column(name = "OFFER_VER_ID", nullable = false)
    private Integer offerVerId;

    public Integer getRefValueId() {
        return refValueId;
    }

    public void setRefValueId(Integer refValueId) {
        this.refValueId = refValueId;
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
        RefValueToOfferVerId entity = (RefValueToOfferVerId) o;
        return Objects.equals(this.refValueId, entity.refValueId) &&
                Objects.equals(this.offerVerId, entity.offerVerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refValueId, offerVerId);
    }

}