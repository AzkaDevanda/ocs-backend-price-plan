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

@Getter
@Setter
@Entity
@Table(name = "DEBIT_PAYMENT")
public class DebitPayment {
  @NotNull
  @Column(name = "ACCT_BOOK_ID", nullable = false)
  private Long acctBookId;

  @NotNull
  @Column(name = "DEBIT_ITEM_ID", nullable = false)
  private Long debitItemId;

  @Size(max = 4000)
  @Column(name = "REF_ATTR", length = 4000)
  private String refAttr;

  @Column(name = "SP_ID")
  private Integer spId;

  @NotNull
  @ColumnDefault("TO_NUMBER(TO_CHAR(SYSDATE, 'MM'))")
  @Column(name = "PART_ID", nullable = false)
  private Short partId;

  @Id
  private Long id;
}