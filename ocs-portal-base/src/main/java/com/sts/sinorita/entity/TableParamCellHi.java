package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "TABLE_PARAM_CELL_HIS")
public class TableParamCellHi implements Serializable {
    @EmbeddedId
    private TableParamCellHiId id;

    @Column(name = "COL_ID", nullable = false)
    private Integer colId;

    @Column(name = "ZONE_ID", nullable = false)
    private Integer zoneId;

    @Column(name = "CELL_VALUE", length = 60)
    private String cellValue;

    @Column(name = "DISP_CONTENT", length = 120)
    private String dispContent;

    @Column(name = "DISP_PREFIX", length = 120)
    private String dispPrefix;

    @Column(name = "DISP_SUFFIX", length = 120)
    private String dispSuffix;

    @Column(name = "VALUE_TYPE", length = 3)
    private String valueType;

    @Column(name = "RATE_PRECISION")
    private Boolean ratePrecision;

    @Column(name = "LOCK_FLAG")
    private Boolean lockFlag;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "STATE", nullable = false)
    private Boolean state = false;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @ColumnDefault("'N'")
    @Column(name = "TEMPLATE_FLAG")
    private Boolean templateFlag;

    @Column(name = "SRC_CELL_ID")
    private Integer srcCellId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "REC_EFF_DATE")
    private LocalDate recEffDate;

    public TableParamCellHiId getId() {
        return id;
    }

    public void setId(TableParamCellHiId id) {
        this.id = id;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getCellValue() {
        return cellValue;
    }

    public void setCellValue(String cellValue) {
        this.cellValue = cellValue;
    }

    public String getDispContent() {
        return dispContent;
    }

    public void setDispContent(String dispContent) {
        this.dispContent = dispContent;
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

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public Boolean getRatePrecision() {
        return ratePrecision;
    }

    public void setRatePrecision(Boolean ratePrecision) {
        this.ratePrecision = ratePrecision;
    }

    public Boolean getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(Boolean lockFlag) {
        this.lockFlag = lockFlag;
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

    public Boolean getTemplateFlag() {
        return templateFlag;
    }

    public void setTemplateFlag(Boolean templateFlag) {
        this.templateFlag = templateFlag;
    }

    public Integer getSrcCellId() {
        return srcCellId;
    }

    public void setSrcCellId(Integer srcCellId) {
        this.srcCellId = srcCellId;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public LocalDate getRecEffDate() {
        return recEffDate;
    }

    public void setRecEffDate(LocalDate recEffDate) {
        this.recEffDate = recEffDate;
    }

}