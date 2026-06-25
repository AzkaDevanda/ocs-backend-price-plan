package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class RpMappingCondGrpDtId implements java.io.Serializable {

    @Column(name = "RP_MAPPING_COND_GRP_ID", nullable = false)
    private Integer rpMappingCondGrpId;

    @Column(name = "SEQ_NO", nullable = false)
    private Short seqNo;

    public Integer getRpMappingCondGrpId() {
        return rpMappingCondGrpId;
    }

    public void setRpMappingCondGrpId(Integer rpMappingCondGrpId) {
        this.rpMappingCondGrpId = rpMappingCondGrpId;
    }

    public Short getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Short seqNo) {
        this.seqNo = seqNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RpMappingCondGrpDtId entity = (RpMappingCondGrpDtId) o;
        return Objects.equals(this.rpMappingCondGrpId, entity.rpMappingCondGrpId) &&
                Objects.equals(this.seqNo, entity.seqNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rpMappingCondGrpId, seqNo);
    }

}