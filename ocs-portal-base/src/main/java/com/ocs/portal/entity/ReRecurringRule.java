package com.ocs.portal.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "RE_RECURRING_RULE")
public class ReRecurringRule implements Serializable {
    @Id
    @Column(name = "RE_ID", nullable = false)
    private Integer id;

    @Column(name = "PRORATE_FLAG")
    private Boolean prorateFlag;

    @Lob
    @Column(name = "SCRIPT_RULE")
    private String scriptRule;

    @Column(name = "SP_ID")
    private Integer spId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getProrateFlag() {
        return prorateFlag;
    }

    public void setProrateFlag(Boolean prorateFlag) {
        this.prorateFlag = prorateFlag;
    }

    public String getScriptRule() {
        return scriptRule;
    }

    public void setScriptRule(String scriptRule) {
        this.scriptRule = scriptRule;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}