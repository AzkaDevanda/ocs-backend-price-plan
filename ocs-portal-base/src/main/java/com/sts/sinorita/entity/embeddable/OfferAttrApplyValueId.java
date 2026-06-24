package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class OfferAttrApplyValueId implements Serializable {

    @Column(name = "OFFER_ID", nullable = false, precision = 9, scale = 0)
    private Long offerId;

    @Column(name = "ATTR_ID", nullable = false, precision = 6, scale = 0)
    private Long attrId;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "ATTR_VALUE_ID", nullable = false, precision = 6, scale = 0)
    private Long attrValueId;
}
