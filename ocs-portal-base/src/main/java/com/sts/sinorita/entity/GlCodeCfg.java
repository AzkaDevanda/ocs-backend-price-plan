package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "GL_CODE_CFG")
public class GlCodeCfg {
  @Id
  @Column(name = "GL_CODE_CFG_ID", nullable = false)
  private Integer id;

  @Column(name = "PRIORITY")
  private Integer priority;

  @NotNull
  @Column(name = "SUBS_EVENT_ID", nullable = false)
  private Integer subsEventId;

  @Column(name = "LEDGER_TYPE")
  private Character ledgerType;

  @Size(max = 255)
  @Column(name = "ACCT_BOOK_TYPE")
  private String acctBookType;

  @Size(max = 255)
  @Column(name = "PAYMENT_METHOD_ID")
  private String paymentMethodId;

  @Size(max = 255)
  @Column(name = "LOAN_TYPE")
  private String loanType;

  @Size(max = 255)
  @Column(name = "DEPOSIT_TYPE_ID")
  private String depositTypeId;

  @Size(max = 255)
  @Column(name = "ACCT_RES_ID")
  private String acctResId;

  @Size(max = 255)
  @Column(name = "REASON_ID")
  private String reasonId;

  @Size(max = 255)
  @Column(name = "ACCT_ITEM_TYPE_ID")
  private String acctItemTypeId;

  @Size(max = 30)
  @Column(name = "GL_DIRECTION", length = 30)
  private String glDirection;

  @Column(name = "GL_COEFFICIENT")
  private Short glCoefficient;

  @Size(max = 30)
  @Column(name = "GL_CODE", length = 30)
  private String glCode;

  @Column(name = "SP_ID")
  private Integer spId;

}