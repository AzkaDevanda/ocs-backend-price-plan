package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "NB_BILL_SEGMENT")
public class NbBillSegment {
  @Id
  @Column(name = "BILL_ID", nullable = false)
  private Long id;

  @Column(name = "BATCH_ID")
  private Long batchId;

  @Column(name = "BILLING_CYCLE_SEQ")
  private Integer billingCycleSeq;

  @Size(max = 30)
  @Column(name = "BILL_NBR", length = 30)
  private String billNbr;

  @NotNull
  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @NotNull
  @Column(name = "BILLING_CYCLE_ID", nullable = false)
  private Integer billingCycleId;

  @Column(name = "PRE_BALANCE")
  private Long preBalance;

  @Column(name = "DUE")
  private Long due;

  @Column(name = "DISPUTE_CHARGE")
  private Long disputeCharge;

  @Column(name = "RECV_CHARGE")
  private Long recvCharge;

  @Column(name = "PAST_ADJUST_CHARGE")
  private Long pastAdjustCharge;

  @Column(name = "ADJUST_CHARGE")
  private Long adjustCharge;

  @Column(name = "CHARGE_BE_ADJUSTED")
  private Long chargeBeAdjusted;

  @Size(max = 30)
  @Column(name = "BILL_CODE", length = 30)
  private String billCode;

  @Column(name = "SETT_CHARGE")
  private Long settCharge;

  @Column(name = "CHARGE_BE_REVERSED")
  private Long chargeBeReversed;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "PAST_DUE_SUM")
  private Long pastDueSum;

  @Size(max = 255)
  @Column(name = "PAST_DUE")
  private String pastDue;

  @Column(name = "DUE_DATE")
  private LocalDate dueDate;

  @Column(name = "WRITEOFF_CHARGE")
  private Long writeoffCharge;

  @Column(name = "SUSPENSION_DATE")
  private LocalDate suspensionDate;

  @Column(name = "STATE")
  private Character state;

  @Column(name = "STATE_DATE")
  private LocalDate stateDate;

  @Column(name = "CYCLE_BEGIN_DATE")
  private LocalDate cycleBeginDate;

  @Column(name = "CYCLE_END_DATE")
  private LocalDate cycleEndDate;

  @Size(max = 255)
  @Column(name = "XML_URL")
  private String xmlUrl;

  @Size(max = 255)
  @Column(name = "PDF_URL")
  private String pdfUrl;

  @Column(name = "DOCUMENT_DATE")
  private LocalDate documentDate;

  @Column(name = "INVOICING_SETT_CHARGE")
  private Long invoicingSettCharge;

  @Column(name = "SUBS_ID")
  private Long subsId;

}