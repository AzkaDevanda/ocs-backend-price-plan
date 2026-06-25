package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "RP_MAPPING_BRANCH")
public class RpMappingBranch implements Serializable {
    @Id
    @Column(name = "RP_MAPPING_BRANCH_ID", nullable = false)
    private Integer id;

    @Column(name = "RP_MAPPING_BRANCH_NAME", nullable = false, length = 60)
    private String rpMappingBranchName;

    @Column(name = "SP_ID")
    private Integer spId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRpMappingBranchName() {
        return rpMappingBranchName;
    }

    public void setRpMappingBranchName(String rpMappingBranchName) {
        this.rpMappingBranchName = rpMappingBranchName;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}