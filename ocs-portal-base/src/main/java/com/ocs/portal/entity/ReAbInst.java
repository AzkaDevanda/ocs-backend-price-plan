package com.ocs.portal.entity;

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
@Table(name = "RE_AB_INST")
public class ReAbInst {
  @NotNull
  @Column(name = "EVENT_INST_ID", nullable = false)
  private Long eventInstId;

  @NotNull
  @Column(name = "ACCT_BOOK_ID", nullable = false)
  private Long acctBookId;

  @Column(name = "SP_ID")
  private Integer spId;

  @NotNull
  @ColumnDefault("TO_NUMBER(TO_CHAR(SYSDATE, 'MM'))")
  @Column(name = "PART_ID", nullable = false)
  private Short partId;

  @Id
  private Long id;
}