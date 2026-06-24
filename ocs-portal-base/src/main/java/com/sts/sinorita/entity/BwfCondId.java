package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class BwfCondId implements java.io.Serializable {
    private static final long serialVersionUID = 4927701467198948830L;
    @Column(name = "COND_GROUP_ID", nullable = false)
    private Integer condGroupId;

    @Column(name = "SEQ", nullable = false)
    private Integer seq;

    public Integer getCondGroupId() {
        return condGroupId;
    }

    public void setCondGroupId(Integer condGroupId) {
        this.condGroupId = condGroupId;
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
        BwfCondId entity = (BwfCondId) o;
        return Objects.equals(this.condGroupId, entity.condGroupId) &&
                Objects.equals(this.seq, entity.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condGroupId, seq);
    }

}