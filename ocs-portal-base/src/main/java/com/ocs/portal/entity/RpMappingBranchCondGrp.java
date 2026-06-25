package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "RP_MAPPING_BRANCH_COND_GRP")
public class RpMappingBranchCondGrp implements Serializable {

    // FK
    // RP_MAPPING_BRANCH_ID
    // RP_MAPPING_COND_GRP_ID
    @EmbeddedId
    private RpMappingBranchCondGrpId id;

    @Column(name = "EXEC_ORDER", nullable = false)
    private Integer execOrder;

    @Column(name = "SP_ID")
    private Integer spId;

    public RpMappingBranchCondGrpId getId() {
        return id;
    }

    public void setId(RpMappingBranchCondGrpId id) {
        this.id = id;
    }

    public Integer getExecOrder() {
        return execOrder;
    }

    public void setExecOrder(Integer execOrder) {
        this.execOrder = execOrder;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}