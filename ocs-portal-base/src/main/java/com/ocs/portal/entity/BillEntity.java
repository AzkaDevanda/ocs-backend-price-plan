package com.ocs.portal.entity;

import com.ocs.portal.dto.request.balanceAdjustment.BillEx;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "BILL")
public class BillEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bill_id_seq")
  @SequenceGenerator(name = "bill_id_seq", sequenceName = "BILL_ID_SEQ", allocationSize = 1)
  @Column(name = "BILL_ID")
  private Long billId;

  @Column(name = "BILL_NBR")
  private String billNbr;

  @Column(name = "ACCT_ID")
  private Long acctId;

  @Column(name = "BILLING_CYCLE_ID")
  private Long billingCycleId;

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

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "BILL_CODE")
  private String billCode;

  @Column(name = "SETT_CHARGE")
  private Long settCharge;

  @Column(name = "CHARGE_BE_REVERSED")
  private Long chargeBeReversed;

  @Transient
  private String operationType;

  @Transient
  private BillEx billEx;

}
