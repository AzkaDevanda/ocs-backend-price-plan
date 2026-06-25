package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TRANS_ACCT_RES_CFG", schema = "STS")
public class TransAcctResCfg {

    @Id
    @Column(name = "ACCT_RES_ID")
    private Long id;

    @Column(name = "DAY_THRESHOLD")
    private Long dayThreshold;

    @Column(name = "WEEK_THRESHOLD")
    private Long weekThreshold;

    @Column(name = "MONTH_THRESHOLD")
    private Long monthThreshold;

    @Column(name = "DAY_COUNT")
    private Long dayCount;

    @Column(name = "WEEK_COUNT")
    private Long weekCount;

    @Column(name = "MONTH_COUNT")
    private Long monthCount;

    @Column(name = "MIN_RESIDUAL_BAL")
    private Long minResidualBal;

    @Column(name = "MAX_ALLOWED")
    private Long maxAllowed;

    @Column(name = "MIN_ALLOWED")
    private Long minAllowed;

    @Column(name = "TRANSFER_FACTOR")
    private Long transferFactor;
}
