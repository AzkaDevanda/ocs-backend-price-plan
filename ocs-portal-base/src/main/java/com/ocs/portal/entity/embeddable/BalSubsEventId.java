package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class BalSubsEventId implements Serializable {

    @Column(name = "BAL_THRESHOLD_ID", nullable = false)
    private Integer balThresholdId;

    @Column(name = "SUBS_EVENT_ID", nullable = false)
    private Integer subsEventId;

}
