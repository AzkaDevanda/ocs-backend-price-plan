package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class TableParamCellHiId implements java.io.Serializable {
    @Column(name = "CELL_ID", nullable = false)
    private Integer cellId;

    @Column(name = "TABLE_PARAM_ID", nullable = false)
    private Integer tableParamId;

    @Column(name = "PARAM_VER", nullable = false)
    private Short paramVer;

    public Integer getCellId() {
        return cellId;
    }

    public void setCellId(Integer cellId) {
        this.cellId = cellId;
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
        TableParamCellHiId entity = (TableParamCellHiId) o;
        return Objects.equals(this.paramVer, entity.paramVer) &&
                Objects.equals(this.cellId, entity.cellId) &&
                Objects.equals(this.tableParamId, entity.tableParamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paramVer, cellId, tableParamId);
    }

}