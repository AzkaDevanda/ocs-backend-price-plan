package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class TabDpDtId implements java.io.Serializable {

    @Column(name = "DP_ID", nullable = false)
    private Integer dpId;

    @Column(name = "SEQ_NO", nullable = false)
    private Short seqNo;

    public Integer getDpId() {
        return dpId;
    }

    public void setDpId(Integer dpId) {
        this.dpId = dpId;
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
        TabDpDtId entity = (TabDpDtId) o;
        return Objects.equals(this.seqNo, entity.seqNo) &&
                Objects.equals(this.dpId, entity.dpId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seqNo, dpId);
    }

}