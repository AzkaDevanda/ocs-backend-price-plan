package com.sts.sinorita.entity;

import com.sts.sinorita.dto.request.accountConfig.BillingCycleTypeDto;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ACCT", schema = "STS")
public class Acct implements Serializable {

  @Id
  @Column(name = "ACCT_ID", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acct_seq_generator")
  @SequenceGenerator(name = "acct_seq_generator", sequenceName = "ACCT_ID_SEQ", allocationSize = 1)
  private Long acctId;

  @Column(name = "STD_ADDR_ID")
  private Long stdAddrId;

  @Column(name = "PAYMENT_METHOD_ID")
  private Long paymentMethodId;

  @Column(name = "CUST_BILL_DELIVERY_INFO_ID")
  private Long custBillDeliveryInfoId;

  @Column(name = "PARENT_ACCT_ID")
  private Long parentAcctId;

  @Column(name = "CUST_ID")
  private Long custId;

  @Column(name = "BILL_ADDRESS", length = 255)
  private String billAddress;

  @Column(name = "ACCT_NBR", length = 60, nullable = false, unique = true)
  private String acctNbr;

  @Column(name = "ACCT_NAME", length = 120)
  private String acctName;

  @Column(name = "BILLING_CYCLE_TYPE_ID", nullable = false)
  private Long billingCycleTypeId;

  @Column(name = "PAYMENT_TYPE", length = 1, nullable = false)
  private String paymentType;

  @Column(name = "BANK_ID")
  private Integer bankId;

  @Column(name = "BANK_ACCT_NBR", length = 60)
  private String bankAcctNbr;

  @Column(name = "BANK_ACCT_NAME", length = 120)
  private String bankAcctName;

  @Column(name = "BANK_ACCT_EXP_DATE")
  private LocalDateTime bankAcctExpDate;

  @Column(name = "PAYMENT_COMMENTS", length = 255)
  private String paymentComments;

  @Column(name = "BANK_CARD_TYPE")
  private Integer bankCardType;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "UPDATE_DATE", nullable = false)
  private LocalDateTime updateDate;

  @Column(name = "STATE", length = 1, nullable = false)
  private Character state;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDateTime stateDate;

  @Column(name = "POSTPAID", length = 1)
  private String postpaid;

  @Column(name = "ROUTING_ID")
  private Long routingId;

  @Column(name = "BILL_FORMAT_ID")
  private Long billFormatId;

  @Column(name = "DEFAULT_FLAG", length = 1)
  private Character defaultFlag;

  @Column(name = "IS_LOCK", length = 1)
  private String isLock;

  @Column(name = "PARTY_TYPE", length = 1)
  private String partyType;

  @Column(name = "PARTY_CODE", length = 60)
  private String partyCode;

  @Column(name = "NEED_UPLOAD", length = 1)
  private String needUpload;

  @Column(name = "BILL_FLAG", length = 1)
  private String billFlag;

  @Column(name = "BILL_CURRENCY")
  private Long billCurrency;

  @Column(name = "SP_ID")
  private Long spId;

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

  @Transient
  private String deliverMethod;

  @Transient
  private Boolean acctLockFlag;

  @Transient
  private String custName;

  @Transient
  private BillingCycleTypeDto billingCycleTypeDto;

  @Transient
  private Long currentBillingCycleId;

  @Transient
  private BillEntity bill;

  @Transient
  private Boolean recordBill;

  @Transient
  private Bal[] allBalList;

  @Transient
  private Bal[] oldBalList;

  @Transient
  private Bal[] newBalList;

  @Transient
  private Bal[] updateBalList;

  @Transient
  private Subs[] allSubsList;

  @PrePersist
  public void prePersist() {
    if (this.acctNbr == null && this.acctId != null) {
      this.acctNbr = this.acctId.toString();
    }
  }

}
