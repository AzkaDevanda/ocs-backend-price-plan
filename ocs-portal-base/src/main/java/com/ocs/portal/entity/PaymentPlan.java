package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "PAYMENT_PLAN")
public class PaymentPlan {
  @Id
  @NotNull
  @Column(name = "PAYMENT_PLAN_ID", nullable = false)
  private Long paymentPlanId;

  @NotNull
  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @NotNull
  @Column(name = "DUE_AMOUNT", nullable = false)
  private Long dueAmount;

  @Column(name = "SETT_CHARGE")
  private Long settCharge;

  @NotNull
  @Column(name = "DUE_DATE", nullable = false)
  private LocalDate dueDate;

  @NotNull
  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @Column(name = "BILL_ID")
  private Long billId;

  @NotNull
  @Column(name = "PLAN_STATUS", nullable = false)
  private Character planStatus;

  @NotNull
  @Column(name = "INSTALL_STATUS", nullable = false)
  private Character installStatus;

  @NotNull
  @Column(name = "INSTALL_STATUS_DATE", nullable = false)
  private LocalDate installStatusDate;

  @NotNull
  @ColumnDefault("TO_NUMBER(TO_CHAR(SYSDATE, 'MM'))")
  @Column(name = "PART_ID", nullable = false)
  private Short partId;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "TAX_CHARGE")
  private Long taxCharge;

  @Column(name = "SETT_TAX_CHARGE")
  private Long settTaxCharge;

  @Size(max = 30)
  @Column(name = "BILL_NBR", length = 30)
  private String billNbr;

  @Column(name = "SETT_DATE")
  private LocalDate settDate;

  @Column(name = "BE_DISPUTED_CHARGE")
  private Long beDisputedCharge;

}