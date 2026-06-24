package com.sts.sinorita.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "DEBIT_ITEM")
public class DebitItem {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "debit_item_seq_generator")
  @SequenceGenerator(name = "debit_item_seq_generator", sequenceName = "DEBIT_ITEM_ID_SEQ", allocationSize = 1)
  @NotNull
  @Column(name = "DEBIT_ITEM_ID", nullable = false)
  private Long debitItemId;

  @NotNull
  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @NotNull
  @Column(name = "CHARGE", nullable = false)
  private Long charge;

  @NotNull
  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @Column(name = "RATIO")
  private Integer ratio;

  @Column(name = "REVERSED_ITEM_ID")
  private Long reversedItemId;

  @Column(name = "REVERSED_BY_ITEM_ID")
  private Long reversedByItemId;

  @Column(name = "ACCT_BOOK_ID")
  private Long acctBookId;

  @Column(name = "SP_ID")
  private Integer spId;

  @NotNull
  @ColumnDefault("TO_NUMBER(TO_CHAR(SYSDATE, 'MM'))")
  @Column(name = "PART_ID", nullable = false)
  private Short partId;

  @ColumnDefault("'1'")
  @Column(name = "LOAN_TYPE")
  private Character loanType;

  @Column(name = "COMMISSION")
  private Long commission;

  @Size(max = 60)
  @Column(name = "PRICE_PLAN_CODE", length = 60)
  private String pricePlanCode;

  @Column(name = "VOLUME")
  private Long volume;

}