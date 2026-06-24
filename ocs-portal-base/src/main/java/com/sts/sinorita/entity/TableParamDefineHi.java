package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "TABLE_PARAM_DEFINE_HIS")
public class TableParamDefineHi implements Serializable {
    @EmbeddedId
    private TableParamDefineHiId id;

    @Column(name = "PARAM_NAME", nullable = false)
    private String paramName;

    @Column(name = "PARAM_CODE", length = 60)
    private String paramCode;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "QUOTATION_CONSTRAINT_ID")
    private Short quotationConstraintId;

    @Column(name = "STATE", nullable = false)
    private Boolean state = false;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @ColumnDefault("'N'")
    @Column(name = "TEMPLATE_FLAG")
    private Boolean templateFlag;

    @Column(name = "ZONE_MAP_ID", nullable = false)
    private Integer zoneMapId;

    @Column(name = "PARAM_VALUE_TYPE")
    private Short paramValueType;

    @Column(name = "SRC_PARAM_ID")
    private Integer srcParamId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "STD_CODE", length = 30)
    private String stdCode;

    @Column(name = "IS_GLOBAL")
    private Boolean isGlobal;

    public TableParamDefineHiId getId() {
        return id;
    }

    public void setId(TableParamDefineHiId id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Short getQuotationConstraintId() {
        return quotationConstraintId;
    }

    public void setQuotationConstraintId(Short quotationConstraintId) {
        this.quotationConstraintId = quotationConstraintId;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public LocalDate getStateDate() {
        return stateDate;
    }

    public void setStateDate(LocalDate stateDate) {
        this.stateDate = stateDate;
    }

    public LocalDate getEffDate() {
        return effDate;
    }

    public void setEffDate(LocalDate effDate) {
        this.effDate = effDate;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public Boolean getTemplateFlag() {
        return templateFlag;
    }

    public void setTemplateFlag(Boolean templateFlag) {
        this.templateFlag = templateFlag;
    }

    public Integer getZoneMapId() {
        return zoneMapId;
    }

    public void setZoneMapId(Integer zoneMapId) {
        this.zoneMapId = zoneMapId;
    }

    public Short getParamValueType() {
        return paramValueType;
    }

    public void setParamValueType(Short paramValueType) {
        this.paramValueType = paramValueType;
    }

    public Integer getSrcParamId() {
        return srcParamId;
    }

    public void setSrcParamId(Integer srcParamId) {
        this.srcParamId = srcParamId;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public String getStdCode() {
        return stdCode;
    }

    public void setStdCode(String stdCode) {
        this.stdCode = stdCode;
    }

    public Boolean getIsGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(Boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

}