package com.ocs.portal.entity;

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
@Table(name = "DEBIT_NOTE")
public class DebitNote {
  @Id
  @Column(name = "NOTE_ID", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "CHARGE", nullable = false)
  private Long charge;

  @NotNull
  @Column(name = "PAYMENT_CHARGE", nullable = false)
  private Long paymentCharge;

  @NotNull
  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @NotNull
  @Column(name = "STATE_DATE", nullable = false)
  private LocalDate stateDate;

  @NotNull
  @Column(name = "STATE", nullable = false)
  private Character state;

  @Column(name = "SP_ID")
  private Integer spId;

}