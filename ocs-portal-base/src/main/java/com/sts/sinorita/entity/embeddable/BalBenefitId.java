package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class BalBenefitId {

    @Column(name = "BAL_THRESHOLD_ID", nullable = false)
    private Integer balThresholdId;

    @Column(name = "SUB_BAL_TYPE_ID", nullable = false)
    private Integer subBalTypeId;
}
