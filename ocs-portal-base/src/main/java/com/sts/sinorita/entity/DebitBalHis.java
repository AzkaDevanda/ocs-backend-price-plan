package com.sts.sinorita.entity;

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
@Table(name = "DEBIT_BAL_HIS")
public class DebitBalHis {
  @NotNull
  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @NotNull
  @Column(name = "BAL", nullable = false)
  private Long bal;

  @Column(name = "COMMISSION_CHARGE")
  private Long commissionCharge;

  @ColumnDefault("'Y'")
  @Column(name = "IS_COMM_CHARGE_NOT_RET")
  private Boolean isCommChargeNotRet;

  @Column(name = "LAST_DEBIT_CHARGE")
  private Long lastDebitCharge;

  @Column(name = "LAST_DEBIT_DATE")
  private LocalDate lastDebitDate;

  @Column(name = "LAST_DEBIT_BAL_ID")
  private Long lastDebitBalId;

  @ColumnDefault("0")
  @Column(name = "LAST_RET_CHARGE")
  private Long lastRetCharge;

  @Column(name = "LAST_COMM_CHARGE")
  private Long lastCommCharge;

  @ColumnDefault("0")
  @Column(name = "LAST_COMM_RET_CHARGE")
  private Long lastCommRetCharge;

  @Size(max = 4000)
  @Column(name = "LAST_BUT_N_DEBIT_INFO", length = 4000)
  private String lastButNDebitInfo;

  @Column(name = "SP_ID")
  private Integer spId;

  @NotNull
  @ColumnDefault("'1'")
  @Column(name = "LOAN_TYPE", nullable = false)
  private Boolean loanType = false;

  @Column(name = "LAST_RET_DATE")
  private LocalDate lastRetDate;

  @Size(max = 60)
  @Column(name = "PRICE_PLAN_CODE", length = 60)
  private String pricePlanCode;

  @NotNull
  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @NotNull
  @ColumnDefault("TO_NUMBER(TO_CHAR(SYSDATE, 'MM'))")
  @Column(name = "PART_ID", nullable = false)
  private Short partId;
  
  @Id
  private Long id;
}