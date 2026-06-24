package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "BWF_RULE_COND")
public class BwfRuleCond implements Serializable {
    @EmbeddedId
    private BwfRuleCondId id;

    @Column(name = "SORT_OPERATOR")
    private Boolean sortOperator;

    @Column(name = "\"FUNCTION\"", length = 30)
    private String function;

    @Column(name = "RE_ATTR", nullable = false)
    private Integer reAttr;

    @Column(name = "PARAM1")
    private String param1;

    @Column(name = "PARAM2")
    private String param2;

    @Column(name = "IS_CONST", nullable = false)
    private Boolean isConst = false;

    @Column(name = "R_RE_ATTR")
    private Integer rReAttr;

    @Column(name = "R_FUNCTION", length = 30)
    private String rFunction;

    @Column(name = "R_PARAM1")
    private String rParam1;

    @Column(name = "R_PARAM2")
    private String rParam2;

    @Column(name = "OPERAND", length = 30)
    private String operand;

    @Column(name = "ZONE_ID")
    private Integer zoneId;

    @Column(name = "FUNCTION_SCRIPT", length = 4000)
    private String functionScript;

    @Column(name = "R_FUNCTION_SCRIPT", length = 4000)
    private String rFunctionScript;

    @Column(name = "SP_ID")
    private Integer spId;

    public BwfRuleCondId getId() {
        return id;
    }

    public void setId(BwfRuleCondId id) {
        this.id = id;
    }

    public Boolean getSortOperator() {
        return sortOperator;
    }

    public void setSortOperator(Boolean sortOperator) {
        this.sortOperator = sortOperator;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Integer getReAttr() {
        return reAttr;
    }

    public void setReAttr(Integer reAttr) {
        this.reAttr = reAttr;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public Boolean getIsConst() {
        return isConst;
    }

    public void setIsConst(Boolean isConst) {
        this.isConst = isConst;
    }

    public Integer getRReAttr() {
        return rReAttr;
    }

    public void setRReAttr(Integer rReAttr) {
        this.rReAttr = rReAttr;
    }

    public String getRFunction() {
        return rFunction;
    }

    public void setRFunction(String rFunction) {
        this.rFunction = rFunction;
    }

    public String getRParam1() {
        return rParam1;
    }

    public void setRParam1(String rParam1) {
        this.rParam1 = rParam1;
    }

    public String getRParam2() {
        return rParam2;
    }

    public void setRParam2(String rParam2) {
        this.rParam2 = rParam2;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getFunctionScript() {
        return functionScript;
    }

    public void setFunctionScript(String functionScript) {
        this.functionScript = functionScript;
    }

    public String getRFunctionScript() {
        return rFunctionScript;
    }

    public void setRFunctionScript(String rFunctionScript) {
        this.rFunctionScript = rFunctionScript;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}