package com.sts.sinorita.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "BAL_SHARE", schema = "STS")
public class BalShare {

  @Id
  @Column(name = "BAL_SHARE_ID", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bal_share_seq_generator")
  @SequenceGenerator(name = "bal_share_seq_generator", sequenceName = "BAL_SHARE_ID_SEQ", allocationSize = 1)
  private Long balShareId;

  @Column(name = "ACCT_ID")
  private Long acctId;

  @Column(name = "EFF_DATE", nullable = false)
  private LocalDate effDate;

  @Column(name = "EXP_DATE")
  private LocalDate expDate;

  @Column(name = "CEIL_LIMIT", precision = 18, scale = 0)
  private BigDecimal ceilLimit;

  @Column(name = "PAYMENT_FORCE", nullable = false, length = 1)
  private String paymentForce;

  @Column(name = "DAILY_CEIL_LIMIT", precision = 18, scale = 0)
  private BigDecimal dailyCeilLimit;

  @Column(name = "SHARE_TYPE", length = 1)
  private String shareType;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "IS_HYBRID", length = 1)
  private String isHybrid;

  @Column(name = "OWNER_SUBS_ID")
  private Long ownerSubsId;

  @Column(name = "VAR_CEIL_LIMIT", precision = 18, scale = 0)
  private BigDecimal varCeilLimit;

  @Column(name = "BS_TEMPLATE_ID")
  private Long bsTemplateId;

  @Column(name = "BILLING_CYCLE_ID")
  private Long billingCycleId;
}