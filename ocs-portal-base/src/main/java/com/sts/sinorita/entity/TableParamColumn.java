package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "TABLE_PARAM_COLUMN")
public class TableParamColumn implements Serializable {
    @Id
    @Column(name = "COL_ID", nullable = false)
    private Integer id;

    @Column(name = "COL_NAME", nullable = false, length = 60)
    private String colName;

    @Column(name = "SEQ")
    private Short seq;

    @Column(name = "TABLE_PARAM_ID", nullable = false)
    private Integer tableParamId;

    @Column(name = "PARAM_VER")
    private Short paramVer;

    @Column(name = "COL_EDIT_MODE")
    private Short colEditMode;

    @Column(name = "QUOTED_PRICE_TYPE")
    private Short quotedPriceType;

    @Column(name = "PARAM_VALUE_TYPE")
    private Short paramValueType;

    @Column(name = "COL_CODE", length = 60)
    private String colCode;

    @Column(name = "MEASURE_UNIT_ID")
    private Integer measureUnitId;

    @Column(name = "QUOTED_UNIT_ID")
    private Integer quotedUnitId;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "STATE", nullable = false)
    private Boolean state = false;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "ZONE_FLAG")
    private Boolean zoneFlag;

    @Column(name = "ACM_FLAG")
    private Boolean acmFlag;

    @Column(name = "EDIT_FLAG")
    private Boolean editFlag;

    @Column(name = "VISABLE_FLAG")
    private Boolean visableFlag;

    @ColumnDefault("'N'")
    @Column(name = "TEMPLATE_FLAG")
    private Boolean templateFlag;

    @Column(name = "SRC_COL_ID")
    private Integer srcColId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "SUM_COL_FLAG")
    private Boolean sumColFlag;

    @Column(name = "IS_BENEFIT")
    private Boolean isBenefit;

    @Column(name = "RELATED_COL_ID")
    private Integer relatedColId;

    @Column(name = "QUOTE_DISP_STYLE")
    private Boolean quoteDispStyle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public Short getSeq() {
        return seq;
    }

    public void setSeq(Short seq) {
        this.seq = seq;
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

    public Short getColEditMode() {
        return colEditMode;
    }

    public void setColEditMode(Short colEditMode) {
        this.colEditMode = colEditMode;
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

    public String getColCode() {
        return colCode;
    }

    public void setColCode(String colCode) {
        this.colCode = colCode;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public Boolean getZoneFlag() {
        return zoneFlag;
    }

    public void setZoneFlag(Boolean zoneFlag) {
        this.zoneFlag = zoneFlag;
    }

    public Boolean getAcmFlag() {
        return acmFlag;
    }

    public void setAcmFlag(Boolean acmFlag) {
        this.acmFlag = acmFlag;
    }

    public Boolean getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(Boolean editFlag) {
        this.editFlag = editFlag;
    }

    public Boolean getVisableFlag() {
        return visableFlag;
    }

    public void setVisableFlag(Boolean visableFlag) {
        this.visableFlag = visableFlag;
    }

    public Boolean getTemplateFlag() {
        return templateFlag;
    }

    public void setTemplateFlag(Boolean templateFlag) {
        this.templateFlag = templateFlag;
    }

    public Integer getSrcColId() {
        return srcColId;
    }

    public void setSrcColId(Integer srcColId) {
        this.srcColId = srcColId;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Boolean getSumColFlag() {
        return sumColFlag;
    }

    public void setSumColFlag(Boolean sumColFlag) {
        this.sumColFlag = sumColFlag;
    }

    public Boolean getIsBenefit() {
        return isBenefit;
    }

    public void setIsBenefit(Boolean isBenefit) {
        this.isBenefit = isBenefit;
    }

    public Integer getRelatedColId() {
        return relatedColId;
    }

    public void setRelatedColId(Integer relatedColId) {
        this.relatedColId = relatedColId;
    }

    public Boolean getQuoteDispStyle() {
        return quoteDispStyle;
    }

    public void setQuoteDispStyle(Boolean quoteDispStyle) {
        this.quoteDispStyle = quoteDispStyle;
    }

}