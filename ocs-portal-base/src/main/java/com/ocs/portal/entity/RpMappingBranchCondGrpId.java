package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class RpMappingBranchCondGrpId implements java.io.Serializable {

    @Column(name = "RP_MAPPING_BRANCH_ID", nullable = false)
    private Integer rpMappingBranchId;

    @Column(name = "RP_MAPPING_COND_GRP_ID", nullable = false)
    private Integer rpMappingCondGrpId;

    public Integer getRpMappingBranchId() {
        return rpMappingBranchId;
    }

    public void setRpMappingBranchId(Integer rpMappingBranchId) {
        this.rpMappingBranchId = rpMappingBranchId;
    }

    public Integer getRpMappingCondGrpId() {
        return rpMappingCondGrpId;
    }

    public void setRpMappingCondGrpId(Integer rpMappingCondGrpId) {
        this.rpMappingCondGrpId = rpMappingCondGrpId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RpMappingBranchCondGrpId entity = (RpMappingBranchCondGrpId) o;
        return Objects.equals(this.rpMappingCondGrpId, entity.rpMappingCondGrpId) &&
                Objects.equals(this.rpMappingBranchId, entity.rpMappingBranchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rpMappingCondGrpId, rpMappingBranchId);
    }

}