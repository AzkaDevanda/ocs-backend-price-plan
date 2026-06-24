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
@Table(name = "PAYMENT_SETT")
public class PaymentSett {
  @Id
  @Column(name = "PAYMENT_SETT_ID", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "ACCT_BOOK_ID", nullable = false)
  private Long acctBookId;

  @NotNull
  @Column(name = "CHARGE", nullable = false)
  private Long charge;

  @NotNull
  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "TOLERANCE")
  private Long tolerance;

}