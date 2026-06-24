package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "CUST_CONTRACT_ITEM", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustContractItem {

  @Id
  @Column(name = "CONTRACT_ITEM_ID", nullable = false)
  private Long contractItemId;

  @Column(name = "CONTRACT_ID", nullable = false)
  private Long contractId;

  @Column(name = "PARENT_CONTRACT_ITEM_ID")
  private Long parentContractItemId;

  @Column(name = "SUBS_PLAN_ID")
  private Long subsPlanId;

  @Column(name = "BUNDLE_MEM_FLAG", length = 1)
  private String bundleMemFlag;

  @Column(name = "CONTRACT_PERIOD_TYPE", length = 1)
  private String contractPeriodType;

  @Column(name = "CONTRACT_DATE")
  private LocalDate contractDate;

  @Column(name = "CONTRACT_PERIOD")
  private Integer contractPeriod;

  @Column(name = "TIME_UNIT", length = 1)
  private String timeUnit;

  @Column(name = "MIN_QTY")
  private Integer minQty;

  @Column(name = "MAX_QTY")
  private Integer maxQty;

  @Column(name = "QUOTATION_ITEM_ID")
  private Long quotationItemId;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "IS_PRIME", length = 1)
  private String isPrime;

  @Column(name = "IS_USED", length = 1)
  private String isUsed;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @Column(name = "EFF_DATE", nullable = false)
  private LocalDate effDate;

  @Column(name = "EXP_DATE")
  private LocalDate expDate;

  @Column(name = "MSA_FLAG", length = 1)
  private String msaFlag;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "QTY")
  private Long qty;

  @Column(name = "ITEM_TYPE", length = 1)
  private String itemType;

  @Column(name = "CONTRACT_ITEM_CODE", length = 60)
  private String contractItemCode;

  @Column(name = "CONTRACT_ITEM_NAME", length = 255)
  private String contractItemName;
}
