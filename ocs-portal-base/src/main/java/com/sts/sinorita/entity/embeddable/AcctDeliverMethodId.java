package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AcctDeliverMethodId {

    @Column(name = "ACCT_ID", nullable = false)
    private Long acctId;

    @Column(name = "DELIVER_METHOD", nullable = false, length = 1)
    private String deliverMethod;
}
