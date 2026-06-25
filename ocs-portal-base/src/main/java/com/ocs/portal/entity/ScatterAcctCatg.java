package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "SCATTER_ACCT_CATG")
@Data
public class ScatterAcctCatg implements Serializable {
    @Id
    @Column(name = "SCATTER_ACCT_CATG_ID", nullable = false)
    private Long scatterAcctCatgId;

    @Column(name = "ACCT_CATG_ID", nullable = false)
    private Long acctCatgId;

    @Column(name = "ACCT_ID", nullable = false)
    private Long acctId;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "PARTY_TYPE", nullable = false, length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 60)
    private String partyCode;

    @Column(name = "SP_ID")
    private Long spId;
}
