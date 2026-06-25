package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CustHisId {
    @Column(name = "CUST_ID", nullable = false)
    private Long custId;

    @Column(name = "SEQ", nullable = false)
    private Long seq;
}
