package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AcctResRestrictId {

    @Column(name = "ACCT_RES_ID", nullable = false)
    private Long acctResId;

    @Column(name = "SEQ", nullable = false)
    private Integer seq;
}
