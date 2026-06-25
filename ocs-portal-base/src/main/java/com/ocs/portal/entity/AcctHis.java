package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "ACCT_HIS", schema = "STS")
@IdClass(AcctHis.Pk.class)
public class AcctHis implements Serializable {

    @Id
    @Column(name = "ACCT_ID", nullable = false)
    private Long acctId;

    @Id
    @Column(name = "SEQ", nullable = false)
    private Long seq;

    // --- Kolom lainnya ---
    @Column(name = "CUST_BILL_DELIVERY_INFO_ID")
    private Long custBillDeliveryInfoId;

    @Column(name = "STD_ADDR_ID")
    private Long stdAddrId;

    @Column(name = "BILL_ADDRESS", length = 255)
    private String billAddress;

    @Column(name = "ACCT_NAME", length = 120)
    private String acctName;

    @Column(name = "PARENT_ACCT_ID")
    private Long parentAcctId;

    @Column(name = "PAYMENT_METHOD_ID")
    private Long paymentMethodId;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "ACCT_NBR", length = 60)
    private String acctNbr;

    @Column(name = "BILLING_CYCLE_TYPE_ID")
    private Long billingCycleTypeId;

    @Column(name = "PAYMENT_TYPE", length = 1)
    private String paymentType;

    @Column(name = "BANK_ID")
    private Long bankId;

    @Column(name = "BANK_ACCT_NBR", length = 60)
    private String bankAcctNbr;

    @Column(name = "BANK_ACCT_NAME", length = 120)
    private String bankAcctName;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "STATE", length = 1)
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDateTime stateDate;

    @Column(name = "POSTPAID", length = 1)
    private String postpaid;

    @Column(name = "ROUTING_ID")
    private Long routingId;

    @Column(name = "BILL_FORMAT_ID")
    private Long billFormatId;

    @Column(name = "PARTY_TYPE", length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "REC_CREATED_DATE")
    private LocalDateTime recCreatedDate;

    @Column(name = "PAYMENT_COMMENTS", length = 255)
    private String paymentComments;

    @Column(name = "DEFAULT_ACC_NBR", length = 30)
    private String defaultAccNbr;

    @Column(name = "BANK_ACCT_EXP_DATE")
    private LocalDateTime bankAcctExpDate;

    @Column(name = "BANK_CARD_TYPE")
    private Integer bankCardType;

    @Column(name = "DEFAULT_FLAG", length = 1)
    private String defaultFlag;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "NEED_UPLOAD", length = 1)
    private String needUpload;

    @Lob
    @Column(name = "EX_ACCT_ATTR")
    private String exAcctAttr;

    @Column(name = "BILL_FLAG", length = 1)
    private String billFlag;

    @Lob
    @Column(name = "EXT_ATTR")
    private String extAttr;

    @Column(name = "BILL_CURRENCY")
    private Long billCurrency;

    @Column(name = "BANK_ACCT_ISSUE_DATE")
    private LocalDateTime bankAcctIssueDate;

    @Column(name = "DEF_LANG_ID")
    private Long defLangId;

    @Column(name = "ALLOW_MOD_STATE_DATE")
    private LocalDateTime allowModStateDate;

    @Column(name = "CREATE_PARTY_TYPE", length = 1)
    private String createPartyType;

    @Column(name = "CREATE_PARTY_CODE", length = 30)
    private String createPartyCode;

    // ----- Composite Primary Key Class -----
    @Data
    public static class Pk implements Serializable {

        private Long acctId;
        private Long seq;     // ✅ hanya dua field ini

        public Pk() {
        }

        public Pk(Long acctId, Long seq) {
            this.acctId = acctId;
            this.seq = seq;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pk pk)) return false;
            return Objects.equals(acctId, pk.acctId) &&
                    Objects.equals(seq, pk.seq);
        }

        @Override
        public int hashCode() {
            return Objects.hash(acctId, seq);
        }
    }
}
