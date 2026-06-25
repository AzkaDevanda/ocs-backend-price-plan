package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "INSTANT_PAYMENT")
public class InstantPayment implements Serializable {
    @Id
    @Column(name = "PAYMENT_ID", nullable = false)
    private Long paymentId;

    @Column(name = "PARTY_TYPE", nullable = false, length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "PAYMENT_METHOD_ID", nullable = false)
    private Integer paymentMethodId;

    @Column(name = "REVERSED_PAYMENT_ID")
    private Long reversedPaymentId;

    @Column(name = "VOUCHER", length = 255)
    private String voucher;

    @Column(name = "SUBMIT_AMOUNT")
    private Long submitAmount;

    @Column(name = "RETURN_AMOUNT")
    private Long returnAmount;

    @Column(name = "REVERSED_REASON", length = 4000)
    private String reversedReason;

    @Column(name = "EVENT_PAYMENT_ID")
    private Long eventPaymentId;

    @Column(name = "BANK_ID")
    private Integer bankId;

    @Column(name = "CHECK_NBR", length = 120)
    private String checkNbr;

    @Column(name = "CHECK_OWNER_NAME", length = 255)
    private String checkOwnerName;

    @Column(name = "CHECK_ISSUE_DATE", length = 30)
    private String checkIssueDate;

    @Column(name = "CHECK_EXP_DATE", length = 30)
    private String checkExpDate;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "CHARGE", nullable = false)
    private Long charge;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "ORI_ACCT_RES_ID")
    private Integer oriAcctResId;

    @Column(name = "DEST_ACCT_RES_ID")
    private Integer destAcctResId;

    @Column(name = "DEST_AMOUNT")
    private Long destAmount;

    @Column(name = "ATTACH_FILE", length = 255)
    private String attachFile;
}
