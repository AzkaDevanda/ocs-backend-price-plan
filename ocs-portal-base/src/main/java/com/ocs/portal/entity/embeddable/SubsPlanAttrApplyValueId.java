package com.ocs.portal.entity.embeddable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class SubsPlanAttrApplyValueId implements Serializable {

    @Column(name = "SUBS_PLAN_OFFER_ATTR_ID", nullable = false, precision = 9, scale = 0)
    private Long subsPlanOfferAttrId;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "ATTR_VALUE_ID", nullable = false, precision = 6, scale = 0)
    private Long attrValueId;
}

