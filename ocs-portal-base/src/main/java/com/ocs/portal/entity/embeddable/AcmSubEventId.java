package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class AcmSubEventId implements Serializable {

    @Column(name = "ACM_THRESHOLD_ID", nullable = false)
    private Integer acmThresholdId;

    @Column(name = "SUBS_EVENT_ID", nullable = false)
    private Integer subsEventId;
}
