package com.sts.sinorita.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "BILLING_TRANSACTION")
public class BillingTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq_generator")
  @SequenceGenerator(name = "transaction_seq_generator", sequenceName = "TRANSACTION_ID_SEQ", allocationSize = 1)
  @NotNull
  @Column(name = "TRANSACTION_ID", nullable = false)
  private Long transactionId;

  @NotNull
  @Column(name = "SEQ", nullable = false)
  private Short seq;

  @NotNull
  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @Column(name = "SUBS_ID")
  private Long subsId;

  @NotNull
  @Column(name = "SUBS_EVENT_ID", nullable = false)
  private Integer subsEventId;

  @Column(name = "ACCT_BOOK_TYPE")
  private Boolean acctBookType;

  @Column(name = "PAYMENT_METHOD_ID")
  private Short paymentMethodId;

  @Column(name = "LOAN_TYPE")
  private Boolean loanType;

  @Column(name = "DEPOSIT_TYPE_ID")
  private Integer depositTypeId;

  @Column(name = "ACCT_RES_ID")
  private Integer acctResId;

  @Column(name = "REASON_ID")
  private Short reasonId;

  @Column(name = "REVERSED_FLAG")
  private Boolean reversedFlag;

  @Column(name = "OFFER_ID")
  private Integer offerId;

  @Column(name = "PARTNER")
  private Boolean partner;

  @Size(max = 120)
  @Column(name = "PARTNER_SN", length = 120)
  private String partnerSn;

  @Size(max = 3000)
  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Size(max = 4000)
  @Column(name = "REF_ATTR", length = 4000)
  private String refAttr;

  @Column(name = "REVERSED_ID")
  private Long reversedId;

  @Column(name = "PARTY_TYPE")
  private Boolean partyType;

  @Size(max = 30)
  @Column(name = "PARTY_CODE", length = 30)
  private String partyCode;

  @Column(name = "CONTACT_CHANNEL_ID")
  private Short contactChannelId;

  @Column(name = "EVENT_PAYMENT_ID")
  private Long eventPaymentId;

  @Column(name = "EVENT_INST_ID")
  private Long eventInstId;

  @Column(name = "DEBIT_ITEM_ID")
  private Long debitItemId;

  @Column(name = "ACCT_BOOK_ID")
  private Long acctBookId;

  @Column(name = "ORDER_ITEM_ID")
  private Long orderItemId;

  @Column(name = "DEPOSIT_ITEM_ID")
  private Long depositItemId;

  @Column(name = "RESERVE_ID1")
  private Long reserveId1;

  @Column(name = "RESERVE_ID2")
  private Long reserveId2;

  @Column(name = "RESERVE_ID3")
  private Long reserveId3;

  @Column(name = "RESERVE_ID4")
  private Long reserveId4;

  @Column(name = "PRE_BALANCE1")
  private Long preBalance1;

  @Size(max = 30)
  @Column(name = "GL_DIRECTION1", length = 30)
  private String glDirection1;

  @Size(max = 30)
  @Column(name = "GL_CODE1", length = 30)
  private String glCode1;

  @Column(name = "CHARGE1")
  private Long charge1;

  @Column(name = "PRE_BALANCE2")
  private Long preBalance2;

  @Size(max = 30)
  @Column(name = "GL_DIRECTION2", length = 30)
  private String glDirection2;

  @Size(max = 30)
  @Column(name = "GL_CODE2", length = 30)
  private String glCode2;

  @Column(name = "CHARGE2")
  private Long charge2;

  @Column(name = "PRE_BALANCE3")
  private Long preBalance3;

  @Size(max = 30)
  @Column(name = "GL_DIRECTION3", length = 30)
  private String glDirection3;

  @Size(max = 30)
  @Column(name = "GL_CODE3", length = 30)
  private String glCode3;

  @Column(name = "CHARGE3")
  private Long charge3;

  @Column(name = "PRE_BALANCE4")
  private Long preBalance4;

  @Size(max = 30)
  @Column(name = "GL_DIRECTION4", length = 30)
  private String glDirection4;

  @Size(max = 30)
  @Column(name = "GL_CODE4", length = 30)
  private String glCode4;

  @Column(name = "CHARGE4")
  private Long charge4;

  @Column(name = "PRE_BALANCE5")
  private Long preBalance5;

  @Size(max = 30)
  @Column(name = "GL_DIRECTION5", length = 30)
  private String glDirection5;

  @Size(max = 30)
  @Column(name = "GL_CODE5", length = 30)
  private String glCode5;

  @Column(name = "CHARGE5")
  private Long charge5;

  @Column(name = "PRE_BALANCE6")
  private Long preBalance6;

  @Size(max = 30)
  @Column(name = "GL_DIRECTION6", length = 30)
  private String glDirection6;

  @Size(max = 30)
  @Column(name = "GL_CODE6", length = 30)
  private String glCode6;

  @Column(name = "CHARGE6")
  private Long charge6;

  @Column(name = "PRE_BALANCE7")
  private Long preBalance7;

  @Size(max = 30)
  @Column(name = "GL_DIRECTION7", length = 30)
  private String glDirection7;

  @Size(max = 30)
  @Column(name = "GL_CODE7", length = 30)
  private String glCode7;

  @Column(name = "CHARGE7")
  private Long charge7;

  @Column(name = "PRE_BALANCE8")
  private Long preBalance8;

  @Size(max = 30)
  @Column(name = "GL_DIRECTION8", length = 30)
  private String glDirection8;

  @Size(max = 30)
  @Column(name = "GL_CODE8", length = 30)
  private String glCode8;

  @Column(name = "CHARGE8")
  private Long charge8;

  @Column(name = "PRE_BALANCE9")
  private Long preBalance9;

  @Size(max = 30)
  @Column(name = "GL_DIRECTION9", length = 30)
  private String glDirection9;

  @Size(max = 30)
  @Column(name = "GL_CODE9", length = 30)
  private String glCode9;

  @Column(name = "CHARGE9")
  private Long charge9;

  @NotNull
  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @NotNull
  @Column(name = "PARTNER_DATE", nullable = false)
  private LocalDate partnerDate;

  @Column(name = "SP_ID")
  private Integer spId;

  @NotNull
  @ColumnDefault("to_number(to_char(sysdate, 'DD'))")
  @Column(name = "PART_ID", nullable = false)
  private Short partId;

  @PrePersist
  protected void onCreate() {
    if (this.createdDate == null) {
      this.createdDate = LocalDate.now();
    }
    if (this.partId == null) {
      // Logika partId otomatis (contoh: berdasarkan bulan)
      this.partId = (short)LocalDateTime.now().getMonthValue();
    }
  }
}