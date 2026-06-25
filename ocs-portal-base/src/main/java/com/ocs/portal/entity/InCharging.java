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
@Table(name = "IN_CHARGING")
public class InCharging implements Serializable {

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

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "BANK_ID")
    private Integer bankId;

    @Column(name = "CHARGE", nullable = false)
    private Long charge;

    @Column(name = "ACC_NBR", nullable = false, length = 60)
    private String accNbr;

    @Column(name = "PREFIX", nullable = false, length = 60)
    private String prefix;

    @Column(name = "IN_DATE", nullable = false)
    private Date inDate;

    @Column(name = "IN_SN", nullable = false, length = 60)
    private String inSn;

    @Column(name = "SUBMIT_AMOUNT")
    private Long submitAmount;

    @Column(name = "RETURN_AMOUNT")
    private Long returnAmount;

    @Column(name = "REVERSED_REASON", length = 4000)
    private String reversedReason;

    @Column(name = "CHECK_NBR", length = 120)
    private String checkNbr;

    @Column(name = "CHECK_OWNER_NAME", length = 255)
    private String checkOwnerName;

    @Column(name = "CHECK_ISSUE_DATE", length = 30)
    private String checkIssueDate;

    @Column(name = "CHECK_EXP_DATE", length = 30)
    private String checkExpDate;

    @Column(name = "SP_ID")
    private Integer spId;
}
