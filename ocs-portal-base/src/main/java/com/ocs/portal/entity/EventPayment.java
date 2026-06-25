package com.ocs.portal.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EVENT_PAYMENT", schema = "STS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventPayment {

    @Id
    @Column(name = "EVENT_PAYMENT_ID", nullable = false)
    private Long eventPaymentId;

    @Column(name = "EVENT_PAYMENT_SN")
    private Long eventPaymentSn;

    @Column(name = "CHARGE", nullable = false, precision = 15, scale = 0)
    private BigDecimal charge;

    @Column(name = "REVERSED_EVENT_PAYMENT_ID")
    private Long reversedEventPaymentId;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "PARTY_TYPE", nullable = false, length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "ORI_CHARGE", precision = 15, scale = 0)
    private BigDecimal oriCharge;

    @Column(name = "REVERSED_BY_PAYMENT_ID")
    private Long reversedByPaymentId;

    @Column(name = "DISCOUNT_CHARGE", precision = 15, scale = 0)
    private BigDecimal discountCharge;

    @Column(name = "PROMOTION_PLAN_ID")
    private Integer promotionPlanId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PART_ID", nullable = false, updatable = false)
    private Integer partId;

    @Column(name = "ORG_ID")
    private Integer orgId;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;

    @Column(name = "RECEIPT_NUM", length = 30)
    private String receiptNum;

    @Column(name = "REFUND_REASON_ID")
    private Integer refundReasonId;
}
