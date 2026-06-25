package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class TableParamColumnSubId implements java.io.Serializable {

    @Column(name = "SUM_COL_ID", nullable = false)
    private Integer sumColId;

    @Column(name = "VALUE_COL_ID", nullable = false)
    private Integer valueColId;

    @Column(name = "TABLE_PARAM_ID", nullable = false)
    private Integer tableParamId;

    @Column(name = "PARAM_VER", nullable = false)
    private Short paramVer;

    public Integer getSumColId() {
        return sumColId;
    }

    public void setSumColId(Integer sumColId) {
        this.sumColId = sumColId;
    }

    public Integer getValueColId() {
        return valueColId;
    }

    public void setValueColId(Integer valueColId) {
        this.valueColId = valueColId;
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
        TableParamColumnSubId entity = (TableParamColumnSubId) o;
        return Objects.equals(this.sumColId, entity.sumColId) &&
                Objects.equals(this.paramVer, entity.paramVer) &&
                Objects.equals(this.valueColId, entity.valueColId) &&
                Objects.equals(this.tableParamId, entity.tableParamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sumColId, paramVer, valueColId, tableParamId);
    }

}