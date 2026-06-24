package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "RP_MAPPING_COND_GRP")
public class RpMappingCondGrp implements Serializable {
    @Id
    @Column(name = "RP_MAPPING_COND_GRP_ID", nullable = false)
    private Integer id;

    @Column(name = "RP_MAPPING_COND_GRP_NAME", nullable = false, length = 60)
    private String rpMappingCondGrpName;

    @Column(name = "SP_ID")
    private Integer spId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRpMappingCondGrpName() {
        return rpMappingCondGrpName;
    }

    public void setRpMappingCondGrpName(String rpMappingCondGrpName) {
        this.rpMappingCondGrpName = rpMappingCondGrpName;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}