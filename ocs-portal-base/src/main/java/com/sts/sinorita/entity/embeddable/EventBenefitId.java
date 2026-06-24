package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Embeddable
@Data
public class EventBenefitId {

    @NotNull
    @Column(name = "EVENT_INST_ID", nullable = false)
    private Long eventInstId;

    @NotNull
    @Column(name = "PRICE_ID", nullable = false)
    private Long priceId;

}
