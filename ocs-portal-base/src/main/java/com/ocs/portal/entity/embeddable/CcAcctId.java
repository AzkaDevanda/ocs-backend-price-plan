package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CcAcctId implements Serializable {

    @Column(name = "ACCT_ID", nullable = false)
    private Long acctId;

    @Column(name = "SEQ", nullable = false)
    private Long seq;

}
