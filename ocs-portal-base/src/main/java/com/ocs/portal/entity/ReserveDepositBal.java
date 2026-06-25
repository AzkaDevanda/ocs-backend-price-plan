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
@Table(name = "RESERVE_DEPOSIT_BAL")
public class ReserveDepositBal {
  @EmbeddedId
  private ReserveDepositBalId id;

  @Column(name = "RESERVE_BAL")
  private Long reserveBal;

  @Column(name = "ORDER_ITEM_ID")
  private Long orderItemId;

  @NotNull
  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @Column(name = "STATE")
  private Boolean state;

  @Column(name = "STATE_DATE")
  private LocalDate stateDate;

  @Column(name = "C_ORDER_ITEM_ID")
  private Long cOrderItemId;

}