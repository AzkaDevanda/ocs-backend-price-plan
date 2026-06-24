package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "SIMPLE_PARAM_DEFINE_HIS")
public class SimpleParamDefineHi implements Serializable {
    @EmbeddedId
    private SimpleParamDefineHiId id;

    @Column(name = "PARAM_NAME", nullable = false)
    private String paramName;

    @Column(name = "PARAM_CODE", length = 60)
    private String paramCode;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "VALUE_TYPE")
    private Boolean valueType;

    @Column(name = "DEF_VALUE", length = 60)
    private String defValue;

    @Column(name = "QUOTED_PRICE_TYPE")
    private Short quotedPriceType;

    @Column(name = "DISP_VALUE", length = 120)
    private String dispValue;

    @Column(name = "PARAM_VALUE_TYPE")
    private Short paramValueType;

    @Column(name = "DISP_PREFIX", length = 120)
    private String dispPrefix;

    @Column(name = "DISP_SUFFIX", length = 120)
    private String dispSuffix;

    @Column(name = "RATE_PRECISION")
    private Boolean ratePrecision;

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

    @Column(name = "SRC_PARAM_ID")
    private Integer srcParamId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "STD_CODE", length = 30)
    private String stdCode;

    @Column(name = "MEASURE_UNIT_ID")
    private Integer measureUnitId;

    @Column(name = "QUOTED_UNIT_ID")
    private Integer quotedUnitId;

    @Column(name = "IS_GLOBAL")
    private Boolean isGlobal;

    @Column(name = "IS_BENEFIT")
    private Boolean isBenefit;

    @Column(name = "EDIT_MODE")
    private Short editMode;

    @Column(name = "ATTR_ID")
    private Integer attrId;

    public SimpleParamDefineHiId getId() {
        return id;
    }

    public void setId(SimpleParamDefineHiId id) {
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

    public Boolean getValueType() {
        return valueType;
    }

    public void setValueType(Boolean valueType) {
        this.valueType = valueType;
    }

    public String getDefValue() {
        return defValue;
    }

    public void setDefValue(String defValue) {
        this.defValue = defValue;
    }

    public Short getQuotedPriceType() {
        return quotedPriceType;
    }

    public void setQuotedPriceType(Short quotedPriceType) {
        this.quotedPriceType = quotedPriceType;
    }

    public String getDispValue() {
        return dispValue;
    }

    public void setDispValue(String dispValue) {
        this.dispValue = dispValue;
    }

    public Short getParamValueType() {
        return paramValueType;
    }

    public void setParamValueType(Short paramValueType) {
        this.paramValueType = paramValueType;
    }

    public String getDispPrefix() {
        return dispPrefix;
    }

    public void setDispPrefix(String dispPrefix) {
        this.dispPrefix = dispPrefix;
    }

    public String getDispSuffix() {
        return dispSuffix;
    }

    public void setDispSuffix(String dispSuffix) {
        this.dispSuffix = dispSuffix;
    }

    public Boolean getRatePrecision() {
        return ratePrecision;
    }

    public void setRatePrecision(Boolean ratePrecision) {
        this.ratePrecision = ratePrecision;
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

    public Integer getMeasureUnitId() {
        return measureUnitId;
    }

    public void setMeasureUnitId(Integer measureUnitId) {
        this.measureUnitId = measureUnitId;
    }

    public Integer getQuotedUnitId() {
        return quotedUnitId;
    }

    public void setQuotedUnitId(Integer quotedUnitId) {
        this.quotedUnitId = quotedUnitId;
    }

    public Boolean getIsGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(Boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

    public Boolean getIsBenefit() {
        return isBenefit;
    }

    public void setIsBenefit(Boolean isBenefit) {
        this.isBenefit = isBenefit;
    }

    public Short getEditMode() {
        return editMode;
    }

    public void setEditMode(Short editMode) {
        this.editMode = editMode;
    }

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

}