package com.sts.sinorita.entity;

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
@Table(name = "INSTALMENT")
public class Instalment {
  @Id
  @Column(name = "INSTLMENT_ID", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "ACCT_ITEM_ID", nullable = false)
  private Long acctItemId;

}