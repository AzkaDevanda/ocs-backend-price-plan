package com.sts.sinorita.entity;

import java.time.LocalDateTime;

import com.sts.sinorita.entity.embeddable.BalShareChangeHisId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BAL_SHARE_CHANGE_HIS", schema = "STS")
public class BalShareChangeHis {

  @EmbeddedId
  private BalShareChangeHisId id;

  @Column(name = "SUBS_ID")
  private Long subsId;

  @Column(name = "ACCT_ID")
  private Long acctId;

  @Column(name = "ACCT_RES_ID")
  private Long acctResId;

  @Column(name = "BAL_ID")
  private Long balId;

  @Column(name = "EFF_DATE")
  private LocalDateTime effDate;

  @Column(name = "EXP_DATE")
  private LocalDateTime expDate;

  @Column(name = "CEIL_LIMIT")
  private Long ceilLimit;

  @Column(name = "PRIORITY")
  private Long priority;

  @Column(name = "PAYMENT_FORCE")
  private String paymentForce;

  @Column(name = "DAILY_CEIL_LIMIT")
  private Long dailyCeilLimit;

  @Column(name = "SHARE_TYPE")
  private String shareType;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "CREATED_DATE")
  private LocalDateTime createdDate;

  @Column(name = "BS_TEMPLATE_ID")
  private Long bsTemplateId;

  @Column(name = "BILLING_CYCLE_ID")
  private Long billingCycleId;

}
