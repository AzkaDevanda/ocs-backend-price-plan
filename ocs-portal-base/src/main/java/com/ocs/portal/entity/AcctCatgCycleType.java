package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "ACCT_CATG_CYCLE_TYPE")
public class AcctCatgCycleType implements Serializable {
    @Id
    @Column(name = "ACCT_CATG_ID", nullable = false)
    private Integer acctCatgId;

    @Column(name = "BILLING_CYCLE_TYPE_ID", nullable = false)
    private Integer billingCycleTypeId;

    @Column(name = "SP_ID")
    private Integer spId;
}
