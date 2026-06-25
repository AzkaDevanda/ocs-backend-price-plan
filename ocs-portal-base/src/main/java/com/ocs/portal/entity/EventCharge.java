package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "EVENT_CHARGE", schema = "STS")
public class EventCharge {

    @Id
    @Column(name = "EVENT_INST_ID", nullable = false)
    private Long eventInstId;

    @Column(name = "PRICE_ID", nullable = false)
    private Long priceId;

    @Column(name = "SEQ", nullable = false)
    private Integer seq;

    @Column(name = "DEDUCT_SEQ", nullable = false)
    private Integer deductSeq;

    @Column(name = "ACCT_ID", nullable = false)
    private Long acctId;

    @Column(name = "CHARGE", nullable = false)
    private Long charge;

    @Column(name = "ACCT_ITEM_ID")
    private Long acctItemId;

    @Column(name = "BILLING_CYCLE_ID")
    private Integer billingCycleId;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "ORI_CHARGE")
    private Long oriCharge;

    @Column(name = "DISCOUNT_CHARGE")
    private Long discountCharge;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "BAL_ID")
    private Long balId;

    @Column(name = "DISCT_PERCENT")
    private Integer disctPercent = 0;

    @Column(name = "ACCT_ITEM_TYPE_ID")
    private Integer acctItemTypeId;

    @Column(name = "BEGIN_DATE")
    private LocalDate beginDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "ACCT_RES_ID")
    private Integer acctResId;

    @Column(name = "REF_ATTR", length = 4000)
    private String refAttr;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "EVENT_PAYMENT_ID")
    private Long eventPaymentId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "PARTY_TYPE", length = 1)
    private String partyType;

    @Column(name = "INSTALMENT_DATE")
    private LocalDateTime instalmentDate;

    @Column(name = "NB_IMM_BATCH_ID")
    private Long nbImmBatchId;

}
