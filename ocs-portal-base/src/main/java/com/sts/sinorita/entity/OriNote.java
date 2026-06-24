package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ORI_NOTE")
public class OriNote implements Serializable {
    @Id
    @Column(name = "NOTE_ID", nullable = false)
    private Long noteId;

    @Column(name = "PAYMENT_METHOD_ID", nullable = false)
    private Integer paymentMethodId;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "BANK_ID")
    private Integer bankId;

    @Column(name = "VOUCHER")
    private String voucher;

    @Column(name = "CHARGE", nullable = false)
    private Long charge;

    @Column(name = "CHECK_NBR")
    private String checkNbr;

    @Column(name = "CHECK_OWNER_NAME")
    private String checkOwnerName;

    @Column(name = "CHECK_ISSUE_DATE")
    private String checkIssueDate;

    @Column(name = "CHECK_EXP_DATE")
    private String checkExpDate;

    @Column(name = "SCRATCH_CARD_PWD")
    private String scratchCardPwd;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "BANK_CARD_TYPE")
    private Integer bankCardType;

    @Column(name = "STATE")
    private String state;

    @Column(name = "STATE_DATE")
    private Date stateDate;

    @Column(name = "PARTY_TYPE")
    private String partyType;

    @Column(name = "PARTY_CODE")
    private String partyCode;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "AUDIT_PARTY_TYPE")
    private String auditPartyType;

    @Column(name = "AUDIT_PARTY_CODE")
    private String auditPartyCode;

    @Column(name = "RECEIVED_STATE")
    private String receivedState = "A";

    @Column(name = "RECEIVED_DATE")
    private Date receivedDate;

    @Column(name = "REC_PARTY_TYPE")
    private String recPartyType;

    @Column(name = "REC_PARTY_CODE")
    private String recPartyCode;

    @Column(name = "CERT_NBR")
    private String certNbr;

    @Column(name = "ORI_ACCT_RES_ID")
    private Integer oriAcctResId;

    @Column(name = "DEST_ACCT_RES_ID")
    private Integer destAcctResId;

    @Column(name = "DEST_AMOUNT")
    private Long destAmount;

    @Column(name = "DATACOM_PARTY_TYPE")
    private String datacomPartyType;

    @Column(name = "DATACOM_PARTY_CODE")
    private String datacomPartyCode;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "COLL_PARTY_TYPE")
    private String collPartyType;

    @Column(name = "COLL_PARTY_CODE")
    private String collPartyCode;
}
