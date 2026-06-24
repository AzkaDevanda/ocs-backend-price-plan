package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class AttrApplyCatgId implements java.io.Serializable {
    @Column(name = "ATTR_ID", nullable = false)
    private Integer attrId;

    @Column(name = "ATTR_CATG", nullable = false)
    private Integer attrCatg;

    public AttrApplyCatgId(Integer attrId, Integer attrCatg) {
        this.attrId = attrId;
        this.attrCatg = attrCatg;
    }

    public AttrApplyCatgId() {
    }

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public Integer getAttrCatg() {
        return attrCatg;
    }

    public void setAttrCatg(Integer attrCatg) {
        this.attrCatg = attrCatg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AttrApplyCatgId entity = (AttrApplyCatgId) o;
        return Objects.equals(this.attrId, entity.attrId) &&
                Objects.equals(this.attrCatg, entity.attrCatg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attrId, attrCatg);
    }

}