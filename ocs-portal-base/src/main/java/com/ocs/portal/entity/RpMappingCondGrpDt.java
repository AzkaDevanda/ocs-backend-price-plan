package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "RP_MAPPING_COND_GRP_DT")
public class RpMappingCondGrpDt implements Serializable {

    // FK
    // RP_MAPPING_COND_GRP
    @EmbeddedId
    private RpMappingCondGrpDtId id;

    @Column(name = "FUNCTION", length = 30)
    private String function;

    @Column(name = "L_RP_REF_COND_ID")
    private Integer lRpRefCondId;

    @Column(name = "L_PARAM1", length = 60)
    private String lParam1;

    @Column(name = "L_PARAM2", length = 60)
    private String lParam2;

    @Column(name = "SORT_OPERATOR")
    private Boolean sortOperator;

    @Column(name = "R_VAL", length = 60)
    private String rVal;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "F_PARAM1", length = 60)
    private String fParam1;

    @Column(name = "F_PARAM2", length = 60)
    private String fParam2;

    public RpMappingCondGrpDtId getId() {
        return id;
    }

    public void setId(RpMappingCondGrpDtId id) {
        this.id = id;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Integer getLRpRefCondId() {
        return lRpRefCondId;
    }

    public void setLRpRefCondId(Integer lRpRefCondId) {
        this.lRpRefCondId = lRpRefCondId;
    }

    public String getLParam1() {
        return lParam1;
    }

    public void setLParam1(String lParam1) {
        this.lParam1 = lParam1;
    }

    public String getLParam2() {
        return lParam2;
    }

    public void setLParam2(String lParam2) {
        this.lParam2 = lParam2;
    }

    public Boolean getSortOperator() {
        return sortOperator;
    }

    public void setSortOperator(Boolean sortOperator) {
        this.sortOperator = sortOperator;
    }

    public String getRVal() {
        return rVal;
    }

    public void setRVal(String rVal) {
        this.rVal = rVal;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public String getFParam1() {
        return fParam1;
    }

    public void setFParam1(String fParam1) {
        this.fParam1 = fParam1;
    }

    public String getFParam2() {
        return fParam2;
    }

    public void setFParam2(String fParam2) {
        this.fParam2 = fParam2;
    }

}