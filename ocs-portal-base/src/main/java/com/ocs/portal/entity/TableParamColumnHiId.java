package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class TableParamColumnHiId implements java.io.Serializable {

    @Column(name = "COL_ID", nullable = false)
    private Integer colId;

    @Column(name = "TABLE_PARAM_ID", nullable = false)
    private Integer tableParamId;

    @Column(name = "PARAM_VER", nullable = false)
    private Short paramVer;

    public Integer getColId() {
        return colId;
    }

    public void setColId(Integer colId) {
        this.colId = colId;
    }

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
        TableParamColumnHiId entity = (TableParamColumnHiId) o;
        return Objects.equals(this.paramVer, entity.paramVer) &&
                Objects.equals(this.colId, entity.colId) &&
                Objects.equals(this.tableParamId, entity.tableParamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paramVer, colId, tableParamId);
    }

}