package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class ZoneValueId implements java.io.Serializable {

    @Column(name = "ZONE_ID", nullable = false)
    private Integer zoneId;

    @Column(name = "SEQ", nullable = false)
    private Integer seq;

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
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
        ZoneValueId entity = (ZoneValueId) o;
        return Objects.equals(this.zoneId, entity.zoneId) &&
                Objects.equals(this.seq, entity.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, seq);
    }

}