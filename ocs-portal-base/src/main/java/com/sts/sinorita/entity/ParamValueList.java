package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "PARAM_VALUE_LIST")
public class ParamValueList implements Serializable {
    @Id
    @Column(name = "PARAM_VALUE_ID", nullable = false)
    private Integer id;

    @Column(name = "RELATED_TYPE", nullable = false)
    private Boolean relatedType = false;

    @Column(name = "RELATED_ID", nullable = false)
    private Integer relatedId;

    @Column(name = "PARAM_NAME", nullable = false, length = 60)
    private String paramName;

    @Column(name = "PARAM_VALUE", nullable = false, length = 30)
    private String paramValue;

    @Column(name = "SP_ID")
    private Integer spId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(Boolean relatedType) {
        this.relatedType = relatedType;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

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

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}