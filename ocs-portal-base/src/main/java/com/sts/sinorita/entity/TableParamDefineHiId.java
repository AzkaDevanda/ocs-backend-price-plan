package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class TableParamDefineHiId implements java.io.Serializable {

    @Column(name = "TABLE_PARAM_ID", nullable = false)
    private Integer tableParamId;

    @Column(name = "PARAM_VER", nullable = false)
    private Short paramVer;

    public Integer getTableParamId() {
        return tableParamId;
    }

    public void setTableParamId(Integer tableParamId) {
        this.tableParamId = tableParamId;
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
        TableParamDefineHiId entity = (TableParamDefineHiId) o;
        return Objects.equals(this.paramVer, entity.paramVer) &&
                Objects.equals(this.tableParamId, entity.tableParamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paramVer, tableParamId);
    }

}