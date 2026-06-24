package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class MappingActionId implements java.io.Serializable {

    @Column(name = "MAPPING_ID", nullable = false)
    private Integer mappingId;

    @Column(name = "SEQ", nullable = false)
    private Integer seq;

    public Integer getMappingId() {
        return mappingId;
    }

    public void setMappingId(Integer mappingId) {
        this.mappingId = mappingId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MappingActionId entity = (MappingActionId) o;
        return Objects.equals(this.mappingId, entity.mappingId) &&
                Objects.equals(this.seq, entity.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mappingId, seq);
    }

}