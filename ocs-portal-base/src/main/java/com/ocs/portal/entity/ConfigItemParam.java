package com.ocs.portal.entity;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONFIG_ITEM_PARAM")
public class ConfigItemParam implements Serializable {

  @EmbeddedId
  private ConfigItemParamPK id;

  @Column(name = "PARAM_NAME")
  private String paramName;

  @Column(name = "PARAM_VALUE")
  private String paramValue;

  @Column(name = "DEFAULT_VALUE")
  private String defaultValue;

  @Column(name = "INPUT_TYPE")
  private String inputType;

  @Column(name = "DATA_TYPE")
  private String dataType;

  @Column(name = "MIN_VALUE")
  private String minValue;

  @Column(name = "MAX_VALUE")
  private String maxValue;

  @Column(name = "VALUE_SCRIPT")
  private String valueScript;

  @Column(name = "UPDATE_DATE")
  private Date updateDate;

  @Column(name = "PARTY_TYPE")
  private String partyType;

  @Column(name = "PARTY_CODE")
  private String partyCode;

  @Column(name = "IS_PAGE_CACHE")
  private String isPageCache;

  @Column(name = "COMMENTS")
  private String comments;

  @Column(name = "CHINESE_COMMENTS")
  private String chineseComments;

  @Column(name = "GRID_FLAG")
  private String gridFlag;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "STATE")
  private String state;

  @ManyToOne
  @JoinColumn(name = "CONFIG_ITEM_ID", referencedColumnName = "CONFIG_ITEM_ID", insertable = false, updatable = false)
  private ConfigItem configItem;

  public String getParamName() {
    return paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public String getParamValue() {
    return paramValue;
  }

  public void setParamValue(String paramValue) {
    this.paramValue = paramValue;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getInputType() {
    return inputType;
  }

  public void setInputType(String inputType) {
    this.inputType = inputType;
  }

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getMinValue() {
    return minValue;
  }

  public void setMinValue(String minValue) {
    this.minValue = minValue;
  }

  public String getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(String maxValue) {
    this.maxValue = maxValue;
  }

  public String getValueScript() {
    return valueScript;
  }

  public void setValueScript(String valueScript) {
    this.valueScript = valueScript;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getPartyType() {
    return partyType;
  }

  public void setPartyType(String partyType) {
    this.partyType = partyType;
  }

  public String getPartyCode() {
    return partyCode;
  }

  public void setPartyCode(String partyCode) {
    this.partyCode = partyCode;
  }

  public String getIsPageCache() {
    return isPageCache;
  }

  public void setIsPageCache(String isPageCache) {
    this.isPageCache = isPageCache;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public String getChineseComments() {
    return chineseComments;
  }

  public void setChineseComments(String chineseComments) {
    this.chineseComments = chineseComments;
  }

  public String getGridFlag() {
    return gridFlag;
  }

  public void setGridFlag(String gridFlag) {
    this.gridFlag = gridFlag;
  }

  public Long getSpId() {
    return spId;
  }

  public void setSpId(Long spId) {
    this.spId = spId;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public ConfigItem getConfigItem() {
    return configItem;
  }

  public void setConfigItem(ConfigItem configItem) {
    this.configItem = configItem;
  }
}