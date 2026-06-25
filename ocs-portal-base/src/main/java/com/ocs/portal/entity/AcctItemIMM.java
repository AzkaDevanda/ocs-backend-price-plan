package com.ocs.portal.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "ACCT_ITEM_IMM")
public class AcctItemIMM implements Serializable {

    @Id
    @Column(name = "BATCH_ID", nullable = false)
    private Integer batchId;

    @Column(name = "SUBS_ID")
    private Integer subsId;

    @Column(name = "ACCT_ID", nullable = false)
    private Integer acctId;

    @Column(name = "ACCT_ITEM_TYPE_ID", nullable = false)
    private Integer acctItemTypeId;

    @Column(name = "BAL_ID")
    private Integer balId;

    @Column(name = "CHARGE", nullable = false)
    private Integer charge;

    @Column(name = "BILLING_CYCLE_ID", nullable = false)
    private Integer billingCycleId;

    @Column(name = "STATE", nullable = false, length = 1)
    private Character state;

    @Column(name = "STATE_DATE", nullable = false)
    private Date stateDate;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "ACCT_RES_ID")
    private Integer acctResId;

    @Column(name = "USE_DURATION")
    private Integer useDuration;

    @Column(name = "USE_FLUX")
    private Integer useFlux;

    @Column(name = "CDR_NUM")
    private Integer cdrNum;

    @Column(name = "PAID_CHARGE")
    private Integer paidCharge;

    @Column(name = "RATE_USAGE")
    private Integer rateUsage;

    @Column(name = "DISCT_PERCENT")
    private Integer disctPercent;

    @Column(name = "GST_SEQ")
    private Integer gstSeq;

    @Column(name = "SRC_APP_ID")
    private Integer srcAppId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "SPLIT_ACCT_ID")
    private Integer splitAcctId;

    @Column(name = "SPLITED_SUBS_ID")
    private Integer splitedSubsId;

    @Column(name = "SPLITED_ACCT_ID")
    private Integer splitedAcctId;

    @Column(name = "SPLIT_SUBS_ID")
    private Integer splitSubsId;

    @Column(name = "SUB_SERVICE_TYPE")
    private Integer subServiceType;

    @Column(name = "ORIGINAL_CHARGE")
    private Long originalCharge;

    @Column(name = "YEARMD")
    private Integer yearmd;

}