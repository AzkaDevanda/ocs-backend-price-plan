package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "SIMPLE_PARAM_DEFINE")
public class SimpleParamDefine implements Serializable {
    @Id
    @Column(name = "SIMPLE_PARAM_ID", nullable = false)
    private Integer id;

    @Column(name = "PARAM_VER", nullable = false)
    private Short paramVer;

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

    @Column(name = "DISP_VALUE", length = 120)
    private String dispValue;

    @Column(name = "DISP_PREFIX", length = 120)
    private String dispPrefix;

    @Column(name = "DISP_SUFFIX", length = 120)
    private String dispSuffix;

    @Column(name = "RATE_PRECISION")
    private Long ratePrecision;

    @Column(name = "STATE", nullable = false)
    private Character state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @ColumnDefault("'N'")
    @Column(name = "TEMPLATE_FLAG")
    private Boolean templateFlag;

    @Column(name = "SRC_PARAM_ID")
    private Integer srcParamId;

    @Column(name = "QUOTED_PRICE_TYPE")
    private Short quotedPriceType;

    @Column(name = "PARAM_VALUE_TYPE")
    private Short paramValueType;

    @Column(name = "QUOTED_UNIT_ID")
    private Integer quotedUnitId;

    @Column(name = "MEASURE_UNIT_ID")
    private Integer measureUnitId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @Column(name = "STD_CODE", length = 30)
    private String stdCode;

    @Column(name = "IS_GLOBAL")
    private Boolean isGlobal;

    @Column(name = "IS_BENEFIT")
    private Boolean isBenefit;

    @Column(name = "EDIT_MODE")
    private Short editMode;

    @Column(name = "ATTR_ID")
    private Integer attrId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getParamVer() {
        return paramVer;
    }

    public void setParamVer(Short paramVer) {
        this.paramVer = paramVer;
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

    public String getDispValue() {
        return dispValue;
    }

    public void setDispValue(String dispValue) {
        this.dispValue = dispValue;
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

    public Long getRatePrecision() {
        return ratePrecision;
    }

    public void setRatePrecision(Long ratePrecision) {
        this.ratePrecision = ratePrecision;
    }

    public Character getState() {
        return state;
    }

    public void setState(Character state) {
        this.state = state;
    }

    public LocalDate getStateDate() {
        return stateDate;
    }

    public void setStateDate(LocalDate stateDate) {
        this.stateDate = stateDate;
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

    public Short getQuotedPriceType() {
        return quotedPriceType;
    }

    public void setQuotedPriceType(Short quotedPriceType) {
        this.quotedPriceType = quotedPriceType;
    }

    public Short getParamValueType() {
        return paramValueType;
    }

    public void setParamValueType(Short paramValueType) {
        this.paramValueType = paramValueType;
    }

    public Integer getQuotedUnitId() {
        return quotedUnitId;
    }

    public void setQuotedUnitId(Integer quotedUnitId) {
        this.quotedUnitId = quotedUnitId;
    }

    public Integer getMeasureUnitId() {
        return measureUnitId;
    }

    public void setMeasureUnitId(Integer measureUnitId) {
        this.measureUnitId = measureUnitId;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
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