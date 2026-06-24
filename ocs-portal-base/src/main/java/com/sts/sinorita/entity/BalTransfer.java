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

@Getter
@Setter
@Entity
@Table(name = "BAL_TRANSFER")
public class BalTransfer {
  @Id
  @NotNull
  @Column(name = "ACCT_BOOK_ID", nullable = false)
  private Long acctBookId;

  @NotNull
  @Column(name = "IN_ACCT_BOOK_ID", nullable = false)
  private Long inAcctBookId;

  @Column(name = "SP_ID")
  private Integer spId;

  @NotNull
  @ColumnDefault("TO_NUMBER(TO_CHAR(SYSDATE, 'MM'))")
  @Column(name = "PART_ID", nullable = false)
  private Short partId;

  @Column(name = "OUT_SUBS_ID")
  private Long outSubsId;

  @Size(max = 60)
  @Column(name = "OUT_PREFIX", length = 60)
  private String outPrefix;

  @Size(max = 60)
  @Column(name = "OUT_ACC_NBR", length = 60)
  private String outAccNbr;

  @Column(name = "IN_SUBS_ID")
  private Long inSubsId;

  @Size(max = 60)
  @Column(name = "IN_PREFIX", length = 60)
  private String inPrefix;

  @Size(max = 60)
  @Column(name = "IN_ACC_NBR", length = 60)
  private String inAccNbr;

  @Size(max = 4000)
  @Column(name = "REF_ATTR", length = 4000)
  private String refAttr;

  @Column(name = "REVERSED_AB_ID")
  private Long reversedAbId;

  @Column(name = "REVERSED_BY_AB_ID")
  private Long reversedByAbId;

  @Column(name = "REVERSED_IN_AB_ID")
  private Long reversedInAbId;

  @Column(name = "REVERSED_BY_IN_AB_ID")
  private Long reversedByInAbId;

  @Size(max = 3000)
  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "TRANSFER_REASON_ID")
  private Short transferReasonId;

}