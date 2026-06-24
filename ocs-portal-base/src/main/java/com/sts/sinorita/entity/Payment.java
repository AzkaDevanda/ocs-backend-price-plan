package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.PaymentId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "PAYMENT")
public class Payment implements Serializable {

    @EmbeddedId
    PaymentId paymentId;

    @Column(name = "REVERSED_PAYMENT_ID")
    private Long reversedPaymentId;

    @Column(name = "PAYMENT_METHOD_ID", nullable = false)
    private Integer paymentMethodId;

    @Column(name = "BANK_ID")
    private Integer bankId;

    @Column(name = "VOUCHER", length = 255)
    private String voucher;

    @Column(name = "SUBMIT_AMOUNT")
    private Long submitAmount;

    @Column(name = "RETURN_AMOUNT")
    private Long returnAmount;

    @Column(name = "REF_ATTR", length = 4000)
    private String refAttr;

    @Column(name = "CHECK_NBR", length = 120)
    private String checkNbr;

    @Column(name = "CHECK_OWNER_NAME", length = 255)
    private String checkOwnerName;

    @Column(name = "CHECK_ISSUE_DATE", length = 30)
    private String checkIssueDate;

    @Column(name = "CHECK_EXP_DATE", length = 30)
    private String checkExpDate;

    @Column(name = "SCRATCH_CARD_PWD", length = 255)
    private String scratchCardPwd;

    @Column(name = "PREFIX", length = 60)
    private String prefix;

    @Column(name = "ACC_NBR", length = 60)
    private String accNbr;

    @Column(name = "PAYMENT_DATE")
    private Date paymentDate;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "ORI_ACCT_RES_ID")
    private Integer oriAcctResId;

    @Column(name = "DEST_ACCT_RES_ID")
    private Integer destAcctResId;

    @Column(name = "DEST_AMOUNT")
    private Long destAmount;

    @Column(name = "REVERSED_BY_PAYMENT_ID")
    private Long reversedByPaymentId;

    @Column(name = "REFUND_REASON_ID")
    private Integer refundReasonId;

    @Column(name = "OVERPAY_AMOUNT")
    private Long overpayAmount;

    @Column(name = "RECEIPT_NUM", length = 30)
    private String receiptNum;

    @Column(name = "ATTACH_FILE", length = 255)
    private String attachFile;
}
