package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "RE_OVERDUE_INST")
public class ReOverdueInst {
  @Id
  @Column(name = "EVENT_INST_ID", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "CAPITAL_AMOUNT", nullable = false)
  private Long capitalAmount;

  @NotNull
  @Column(name = "OVERDUE_DAY", nullable = false)
  private Integer overdueDay;

  @Column(name = "SP_ID")
  private Integer spId;

}