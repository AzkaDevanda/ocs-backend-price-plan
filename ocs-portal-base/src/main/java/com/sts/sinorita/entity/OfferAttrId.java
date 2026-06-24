package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class OfferAttrId implements java.io.Serializable {

    @Column(name = "OFFER_ID", nullable = false)
    private Integer offerId;

    @Column(name = "ATTR_ID", nullable = false)
    private Integer attrId;

    public OfferAttrId(Integer offerId, Integer attrId) {
        this.offerId = offerId;
        this.attrId = attrId;
    }

    public OfferAttrId() {}

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OfferAttrId entity = (OfferAttrId) o;
        return Objects.equals(this.attrId, entity.attrId) &&
                Objects.equals(this.offerId, entity.offerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attrId, offerId);
    }

}