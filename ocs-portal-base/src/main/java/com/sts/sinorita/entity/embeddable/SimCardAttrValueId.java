package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SimCardAttrValueId {
    @Column(name = "SIM_CARD_ID")
    private Long simCardId;

    @Column(name = "ATTR_ID")
    private Long attrId;
}
