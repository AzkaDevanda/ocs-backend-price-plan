package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ADVICE_TYPE_PARAM", schema = "STS")
public class AdviceTypeParam {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advice_param_id_seq")
  @SequenceGenerator(name = "advice_param_id_seq", sequenceName = "ADVICE_PARAM_ID_SEQ", allocationSize = 1)
  @Column(name = "ADVICE_PARAM_ID", nullable = false)
  private Long adviceParamId;

  @Column(name = "ADVICE_TYPE")
  private Long adviceType;

  @Column(name = "ADVICE_PARAM_VALUE_ID")
  private Long adviceParamValueId;

  @Column(name = "ADVICE_PARAM_NAME", nullable = false, length = 255)
  private String adviceParamName;

  @Column(name = "ADVICE_PARAM_CODE", nullable = false, length = 255)
  private String adviceParamCode;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "RULE_FLAG", columnDefinition = "CHAR(1) DEFAULT 'N'")
  private String ruleFlag;

  @Column(name = "IS_MACRO", columnDefinition = "CHAR(1) DEFAULT 'N'")
  private String isMacro;

  @Column(name = "IS_STRING", columnDefinition = "CHAR(1)")
  private String isString;

  @Column(name = "SP_ID")
  private Long spId;
}
