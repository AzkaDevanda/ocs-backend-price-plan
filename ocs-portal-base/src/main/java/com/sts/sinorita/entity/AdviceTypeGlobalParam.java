package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ADVICE_TYPE_GLOBAL_PARAM", schema = "STS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdviceTypeGlobalParam {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advice_global_param_id_seq")
  @SequenceGenerator(name = "advice_global_param_id_seq", sequenceName = "ADVICE_GLOBAL_PARAM_ID_SEQ", allocationSize = 1)
  @Column(name = "ADVICE_GLOBAL_PARAM_ID", nullable = false)
  private Long adviceGlobalParamId;

  @Column(name = "ADVICE_PARAM_CODE", nullable = false, length = 60)
  private String adviceParamCode;

  @Column(name = "ADVICE_CATG", nullable = false, length = 60)
  private String adviceCatg;

  @Column(name = "COMMENTS", length = 255)
  private String comments;

  @Column(name = "SP_ID")
  private Integer spId;
}