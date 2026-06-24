package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Setter
@Getter
@Embeddable
public class TabDpCondGrpDtId implements java.io.Serializable {

    @Column(name = "TAB_DP_COND_GRP_ID", nullable = false)
    private Integer tabDpCondGrpId;

    @Column(name = "GRP_ID", nullable = false)
    private Short grpId;

    @Column(name = "SEQ_NO", nullable = false)
    private Short seqNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TabDpCondGrpDtId entity = (TabDpCondGrpDtId) o;
        return Objects.equals(this.grpId, entity.grpId) &&
                Objects.equals(this.seqNo, entity.seqNo) &&
                Objects.equals(this.tabDpCondGrpId, entity.tabDpCondGrpId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grpId, seqNo, tabDpCondGrpId);
    }

}