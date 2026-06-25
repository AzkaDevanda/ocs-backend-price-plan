package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "VIR_ACCT_RES_RELA", schema = "STS")
public class VirAcctResRela {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vir_rela_id_seq")
  @SequenceGenerator(name = "vir_rela_id_seq", sequenceName = "VIR_RELA_ID_SEQ", allocationSize = 1)
  @Column(name = "VIR_RELA_ID", nullable = false)
  private Long virRelaId;

  @Column(name = "VIR_ACCT_RES_ID", nullable = false)
  private Long virAcctResId;

  @Column(name = "ACCT_RES_ID", nullable = false)
  private Long acctResId;

  @Column(name = "CHARGE_ACCT_ITEM_TYPE_ID")
  private Long chargeAcctItemTypeId;

  @Column(name = "PAY_INDICATOR")
  private String payIndicator;
}
