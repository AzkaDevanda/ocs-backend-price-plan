package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ADVICE_RULE_COND", schema = "STS")
public class AdviceRuleCond {
  @Id
  @Column(name = "ADVICE_RULE_COND_ID", nullable = false)
  private Long adviceRuleCondId;

  @Column(name = "ADVICE_TYPE")
  private Long adviceType;

  @Column(name = "ADVICE_PARAM_ID")
  private Long adviceParamId;

  @Column(name = "OPER", length = 30)
  private String oper;

  @Column(name = "VALUE", length = 255)
  private String value;

  @Column(name = "FAC_VAL_NAME", length = 255)
  private String facValName;

  @Column(name = "SP_ID")
  private Long spId;
}
