package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ACCT_ITEM_INSTALMENT")
public class AcctItemInstalment {
  @EmbeddedId
  private AcctItemInstalmentId id;

  @NotNull
  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @Column(name = "SUBS_ID")
  private Long subsId;

  @NotNull
  @Column(name = "CHARGE", nullable = false)
  private Long charge;

  @Column(name = "ACCT_ITEM_ID")
  private Long acctItemId;

  @Column(name = "BILLING_CYCLE_ID")
  private Integer billingCycleId;

  @NotNull
  @Column(name = "ACCT_ITEM_TYPE_ID", nullable = false)
  private Integer acctItemTypeId;

  @NotNull
  @Column(name = "STATE", nullable = false)
  private Boolean state = false;

  @NotNull
  @Column(name = "STATE_DATE", nullable = false)
  private LocalDate stateDate;

  @Column(name = "DISCT_PERCENT")
  private Integer disctPercent;

  @Column(name = "NB_IMM_BATCH_ID")
  private Long nbImmBatchId;

}