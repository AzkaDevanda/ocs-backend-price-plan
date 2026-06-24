package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "BATCH_ACCT_BOOK")
public class BatchAcctBook {

  @Id
  @NotNull
  @Column(name = "AB_BATCH_ID", nullable = false)
  private Long abBatchId;

  @NotNull
  @Column(name = "ACCT_BOOK_ID", nullable = false)
  private Long acctBookId;

  @Column(name = "SP_ID")
  private Integer spId;

  @NotNull
  @ColumnDefault("TO_NUMBER(TO_CHAR(SYSDATE, 'MM'))")
  @Column(name = "PART_ID", nullable = false)
  private Short partId;

}