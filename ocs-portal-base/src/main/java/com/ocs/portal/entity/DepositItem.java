package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "DEPOSIT_ITEM")
public class DepositItem implements Serializable{
    @Id
    @Column(name = "DEPOSIT_ITEM_ID", nullable = false)
    private Long depositItemId;

    @Column(name = "SUBS_ID", nullable = false)
    private Long subsId;

    @Column(name = "DEPOSIT_TYPE_ID", nullable = false)
    private Integer depositTypeId;

    @Column(name = "VOUCHER", length = 255)
    private String voucher;

    @Column(name = "PARTY_TYPE", length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "CHARGE", nullable = false)
    private Long charge;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "SUBMIT_AMOUNT")
    private Long submitAmount;

    @Column(name = "RETURN_AMOUNT")
    private Long returnAmount;

    @Column(name = "BANK_ID")
    private Integer bankId;

    @Column(name = "EVENT_PAYMENT_ID")
    private Long eventPaymentId;

    @Column(name = "PAYMENT_METHOD_ID")
    private Integer paymentMethodId;

    @Column(name = "CHECK_NBR", length = 120)
    private String checkNbr;

    @Column(name = "CHECK_OWNER_NAME", length = 255)
    private String checkOwnerName;

    @Column(name = "CHECK_ISSUE_DATE", length = 30)
    private String checkIssueDate;

    @Column(name = "CHECK_EXP_DATE", length = 30)
    private String checkExpDate;

    @Column(name = "RECEIPT_NUM", length = 30)
    private String receiptNum;

    @Column(name = "OFFER_ID")
    private Integer offerId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "ROUTING_ID")
    private Integer routingId;

    @Column(name = "STATE", length = 1)
    private String state;

    @Column(name = "STATE_DATE")
    private Date stateDate;

    @Column(name = "ATTACHMENT_FILE", length = 255)
    private String attachmentFile;
}
