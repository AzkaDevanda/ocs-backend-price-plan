package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class SimpleParamDefineHiId implements java.io.Serializable {

    @Column(name = "SIMPLE_PARAM_ID", nullable = false)
    private Integer simpleParamId;

    @Column(name = "PARAM_VER", nullable = false)
    private Short paramVer;

    public Integer getSimpleParamId() {
        return simpleParamId;
    }

    public void setSimpleParamId(Integer simpleParamId) {
        this.simpleParamId = simpleParamId;
    }

    public Short getParamVer() {
        return paramVer;
    }

    public void setParamVer(Short paramVer) {
        this.paramVer = paramVer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SimpleParamDefineHiId entity = (SimpleParamDefineHiId) o;
        return Objects.equals(this.paramVer, entity.paramVer) &&
                Objects.equals(this.simpleParamId, entity.simpleParamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paramVer, simpleParamId);
    }

}