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
@Table(name = "ACCT_RECENT_OPER")
public class AcctRecentOper {
  @Id
  @Column(name = "ACCT_ID", nullable = false)
  private Long id;

  @Size(max = 4000)
  @Column(name = "RECENT_OPER", length = 4000)
  private String recentOper;

  @NotNull
  @Column(name = "UPDATE_DATE", nullable = false)
  private LocalDate updateDate;

}