package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RatePlanMappingId implements java.io.Serializable {

    @Column(name = "RE_ID", nullable = false)
    private Integer reId;

    @Column(name = "OFFER_VER_ID", nullable = false)
    private Integer offerVerId;

    @Column(name = "RATE_PLAN_ID", nullable = false)
    private Integer ratePlanId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RatePlanMappingId entity = (RatePlanMappingId) o;
        return Objects.equals(this.ratePlanId, entity.ratePlanId) &&
                Objects.equals(this.reId, entity.reId) &&
                Objects.equals(this.offerVerId, entity.offerVerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratePlanId, reId, offerVerId);
    }

}