package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ADJUST_PREPARE_ITEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdjustPrepareItem implements Serializable {

    @Id
    @Column(name = "ADJUST_PREPARE_ITEM_ID")
    private Long adjustPrepareItemId;

    @Column(name = "ADJUST_PREPARE_ID", nullable = false)
    private Long adjustPrepareId;

    @Column(name = "ACCT_ITEM_ID")
    private Long acctItemId;

    @Column(name = "BILLING_CYCLE_ID", nullable = false)
    private Integer billingCycleId;

    @Column(name = "CHARGE", nullable = false)
    private BigDecimal charge;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDate createdDate;

    @Column(name = "STATE", nullable = false)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "BAL_ID", nullable = false)
    private Long balId;

    @Column(name = "ACCT_ITEM_TYPE_ID")
    private Integer acctItemTypeId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "GST_SEQ")
    private Integer gstSeq;

    @Column(name = "DISCT_PERCENT")
    private Integer disctPercent;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "NB_IMM_BATCH_ID")
    private Long nbImmBatchId;
}
