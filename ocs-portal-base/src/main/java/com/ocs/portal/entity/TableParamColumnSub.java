package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "TABLE_PARAM_COLUMN_SUB")
public class TableParamColumnSub implements Serializable {
    @EmbeddedId
    private TableParamColumnSubId id;

    @Column(name = "VALUE_PREC")
    private Boolean valuePrec;

    @Column(name = "SP_ID")
    private Integer spId;

    public TableParamColumnSubId getId() {
        return id;
    }

    public void setId(TableParamColumnSubId id) {
        this.id = id;
    }

    public Boolean getValuePrec() {
        return valuePrec;
    }

    public void setValuePrec(Boolean valuePrec) {
        this.valuePrec = valuePrec;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}