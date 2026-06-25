package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class AttrValueId implements java.io.Serializable {
    @Column(name = "BASE_ATTR_ID", nullable = false)
    private Integer baseAttrId;

    @Column(name = "ATTR_VALUE_ID", nullable = false)
    private Integer attrValueId;

    public AttrValueId(Integer baseAttrId,Integer attrValueId) {
        this.baseAttrId = baseAttrId;
        this.attrValueId = attrValueId;
    }

    public AttrValueId() {

    }

    public Integer getBaseAttrId() {
        return baseAttrId;
    }

    public void setBaseAttrId(Integer baseAttrId) {
        this.baseAttrId = baseAttrId;
    }

    public Integer getAttrValueId() {
        return attrValueId;
    }

    public void setAttrValueId(Integer attrValueId) {
        this.attrValueId = attrValueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AttrValueId entity = (AttrValueId) o;
        return Objects.equals(this.baseAttrId, entity.baseAttrId) &&
                Objects.equals(this.attrValueId, entity.attrValueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseAttrId, attrValueId);
    }

}