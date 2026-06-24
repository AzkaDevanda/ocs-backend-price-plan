package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "BWF_ACTION")
public class BwfAction implements Serializable {
    @EmbeddedId
    private BwfActionId id;

    @Column(name = "FUNCTION", length = 30)
    private String function;

    @Column(name = "SRC_RE_ATTR", nullable = false)
    private Integer srcReAttr;

    @Column(name = "OBJ_RE_ATTR", nullable = false)
    private Integer objReAttr;

    @Column(name = "PARAM1")
    private String param1;

    @Column(name = "PARAM2")
    private String param2;

    @Column(name = "FUNCTION_SCRIPT", length = 4000)
    private String functionScript;

    @Column(name = "SP_ID")
    private Integer spId;

    public BwfActionId getId() {
        return id;
    }

    public void setId(BwfActionId id) {
        this.id = id;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Integer getSrcReAttr() {
        return srcReAttr;
    }

    public void setSrcReAttr(Integer srcReAttr) {
        this.srcReAttr = srcReAttr;
    }

    public Integer getObjReAttr() {
        return objReAttr;
    }

    public void setObjReAttr(Integer objReAttr) {
        this.objReAttr = objReAttr;
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

    public String getFunctionScript() {
        return functionScript;
    }

    public void setFunctionScript(String functionScript) {
        this.functionScript = functionScript;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}