package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class MappingUnitId implements java.io.Serializable {

    @Column(name = "MAPPING_ID", nullable = false)
    private Integer mappingId;

    @Column(name = "RATE_PLAN_ZONE_ID", nullable = false)
    private Integer ratePlanZoneId;

    public Integer getMappingId() {
        return mappingId;
    }

    public void setMappingId(Integer mappingId) {
        this.mappingId = mappingId;
    }

    public Integer getRatePlanZoneId() {
        return ratePlanZoneId;
    }

    public void setRatePlanZoneId(Integer ratePlanZoneId) {
        this.ratePlanZoneId = ratePlanZoneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MappingUnitId entity = (MappingUnitId) o;
        return Objects.equals(this.mappingId, entity.mappingId) &&
                Objects.equals(this.ratePlanZoneId, entity.ratePlanZoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mappingId, ratePlanZoneId);
    }

}