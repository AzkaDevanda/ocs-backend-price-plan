package com.ocs.portal.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "INSTANT_INVOICE_PAY", schema = "STS")
public class InstantInvoicePay {
    @Id
    @Column(name = "BATCH_ID")
    private Long batchId;

    @Column(name = "PAYMENT_METHOD_ID", nullable = false)
    private Long paymentMethodId;

    @Column(name = "ACCT_ID", nullable = false)
    private Long acctId;

    @Column(name = "BILLING_CYCLE_ID", nullable = false)
    private Long billingCycleId;

    @Column(name = "CHARGE", nullable = false)
    private Long charge;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "PARTY_TYPE", nullable = false)
    private String partyType;

    @Column(name = "PARTY_CODE")
    private String partyCode;

    @Column(name = "RECEIPT_NUM")
    private String receiptNum;

    @Column(name = "VOUCHER")
    private String voucher;

    @Column(name = "BANK_ID")
    private Long bankId;

    @Column(name = "CHECK_NBR")
    private String checkNbr;

    @Column(name = "CHECK_OWNER_NAME")
    private String checkOwnerName;

    @Column(name = "CHECK_ISSUE_DATE")
    private String checkIssueDate;

    @Column(name = "CHECK_EXP_DATE")
    private String checkExpDate;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "ACCT_BOOK_ID")
    private Long acctBookId;

}
