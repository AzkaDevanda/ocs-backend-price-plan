package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "OVERDUE_ITEM")
public class OverdueItem {
  @EmbeddedId
  private OverdueItemId id;

  @NotNull
  @Column(name = "BILLING_CYCLE_ID", nullable = false)
  private Integer billingCycleId;

  @Column(name = "ACCT_ITEM_TYPE_ID")
  private Integer acctItemTypeId;

  @Column(name = "CHARGE")
  private Long charge;

  @Column(name = "OVERDUE_CHARGE")
  private Long overdueCharge;

  @Column(name = "ADJUST_CHARGE")
  private Long adjustCharge;

  @Column(name = "SP_ID")
  private Integer spId;

}