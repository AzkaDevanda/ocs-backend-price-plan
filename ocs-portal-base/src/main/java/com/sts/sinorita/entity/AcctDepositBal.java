package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ACCT_DEPOSIT_BAL")
public class AcctDepositBal {
  @Id
  @Column(name = "DEPOSIT_ITEM_ID", nullable = false)
  private Long id;

  @Column(name = "ACCT_ID")
  private Long acctId;

  @Column(name = "SUBS_ID")
  private Long subsId;

  @NotNull
  @Column(name = "DEPOSIT_TYPE_ID", nullable = false)
  private Integer depositTypeId;

  @Column(name = "BAL")
  private Long bal;

  @NotNull
  @Column(name = "RESERVE_BAL", nullable = false)
  private Long reserveBal;

  @NotNull
  @Column(name = "EFF_DATE", nullable = false)
  private LocalDate effDate;

  @Column(name = "EXP_DATE")
  private LocalDate expDate;

  @NotNull
  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "ROUTING_ID")
  private Integer routingId;

  @Column(name = "STATE")
  private Boolean state;

  @Column(name = "STATE_DATE")
  private LocalDate stateDate;

}