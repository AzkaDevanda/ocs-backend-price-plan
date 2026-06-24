package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "BWF_RULE_COND_GROUP")
public class BwfRuleCondGroup implements Serializable {
    @Id
    @Column(name = "COND_GROUP_ID", nullable = false)
    private Integer id;

    @Column(name = "RULE_ID", nullable = false)
    private Integer ruleId;

    @Column(name = "SP_ID")
    private Integer spId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}