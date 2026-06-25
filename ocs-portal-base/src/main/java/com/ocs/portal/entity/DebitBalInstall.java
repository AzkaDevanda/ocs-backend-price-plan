package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "DEBIT_BAL_INSTALL")
public class DebitBalInstall {
  @EmbeddedId
  private DebitBalInstallId id;

  @NotNull
  @Column(name = "BAL", nullable = false)
  private Long bal;

  @Column(name = "COMMISSION_CHARGE")
  private Long commissionCharge;

  @ColumnDefault("0")
  @Column(name = "RET_CHARGE")
  private Long retCharge;

  @ColumnDefault("0")
  @Column(name = "COMM_RET_CHARGE")
  private Long commRetCharge;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "LAST_RET_DATE")
  private LocalDate lastRetDate;

}