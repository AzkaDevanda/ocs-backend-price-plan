package com.sts.sinorita.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "BASE_ATTR")
public class BaseAttr implements Serializable {
    @Id
    @Column(name = "BASE_ATTR_ID", nullable = false)
    private Integer id;

    @Column(name = "INPUT_TYPE", nullable = false)
    private Character inputType;

    @Column(name = "NULLABLE", nullable = false)
    private Character nullable;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "DEFAULT_VALUE", length = 60)
    private String defaultValue;

    @Column(name = "VALUE_SCRIPT", length = 4000)
    private String valueScript;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PROMPT_MSG", length = 1000)
    private String promptMsg;

    @Column(name = "FORCE_SELECTION")
    private String forceSelection;

    @Column(name = "DATA_SOURCE_SERVICE")
    private String dataSourceService;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Character getInputType() {
        return inputType;
    }

    public void setInputType(Character inputType) {
        this.inputType = inputType;
    }

    public Character getNullable() {
        return nullable;
    }

    public void setNullable(Character nullable) {
        this.nullable = nullable;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValueScript() {
        return valueScript;
    }

    public void setValueScript(String valueScript) {
        this.valueScript = valueScript;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public String getPromptMsg() {
        return promptMsg;
    }

    public void setPromptMsg(String promptMsg) {
        this.promptMsg = promptMsg;
    }

    public String getForceSelection() {
        return forceSelection;
    }

    public void setForceSelection(String forceSelection) {
        this.forceSelection = forceSelection;
    }

    public String getDataSourceService() {
        return dataSourceService;
    }

    public void setDataSourceService(String dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

}