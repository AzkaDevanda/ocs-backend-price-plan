package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BILL_SHOCK_RULE_RE")
public class BillShockRuleRe {
  @EmbeddedId
  private BillShockRuleReId id;

  @Column(name = "SP_ID")
  private Integer spId;

}