package com.ocs.portal.entity.embeddable;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CcSubsAcmId implements Serializable {

    @Column(name = "SUBS_ID", nullable = false)
    private Long subsId;

    @Column(name = "CC_TYPE", nullable = false)
    private Long ccType;

    @Column(name = "BILLING_CYCLE_ID", nullable = false)
    private Long billingCycleId;

}
