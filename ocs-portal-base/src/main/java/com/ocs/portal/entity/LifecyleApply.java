package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LIFECYCLE_APPLY", schema = "STS")
public class LifecyleApply {
    @Id
    @Column(name = "OFFER_ID", nullable = false)
    private Integer offerId;

    @Column(name = "LIFECYCLE_TYPE", nullable = false)
    private Integer lifeCycleType;

    @Column(name = "SP_ID")
    private Integer spId;
}
