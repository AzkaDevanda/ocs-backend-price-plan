package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Data
@Embeddable
public class BalAdviceId implements Serializable {

    @Column(name = "BAL_THRESHOLD_ID", nullable = false)
    private Integer balThresholdId;

    @ColumnDefault("-1")
    @Column(name = "ADVICE_TYPE")
    private Integer adviceType;
}
