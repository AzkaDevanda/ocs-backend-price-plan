package com.sts.sinorita.entity;

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
@Table(name = "NOTE_PAY_PAYMENT")
public class NotePayPayment {
  @EmbeddedId
  private NotePayPaymentId id;

  @Column(name = "CHARGE")
  private Long charge;

  @NotNull
  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "PRE_BALANCE")
  private Long preBalance;

}