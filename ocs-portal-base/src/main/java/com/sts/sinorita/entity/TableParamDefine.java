package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "TABLE_PARAM_DEFINE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableParamDefine {
  @Id
  @Column(name = "TABLE_PARAM_ID", nullable = false)
  private Long tableParamId;

  @Column(name = "PARAM_VER", nullable = false)
  private Integer paramVer;

  @Column(name = "PARAM_NAME", nullable = false, length = 255)
  private String paramName;

  @Column(name = "PARAM_CODE", length = 60)
  private String paramCode;

  @Column(name = "COMMENTS", length = 255)
  private String comments;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Temporal(TemporalType.DATE)
  @Column(name = "STATE_DATE", nullable = false)
  private Date stateDate;

  @Builder.Default
  @Column(name = "TEMPLATE_FLAG", length = 1)
  private String templateFlag = "N";

  @Column(name = "ZONE_MAP_ID", nullable = false)
  private Long zoneMapId;

  @Column(name = "SRC_PARAM_ID")
  private Long srcParamId;

  @Column(name = "PARAM_VALUE_TYPE")
  private Integer paramValueType;

  @Column(name = "QUOTATION_CONSTRAINT_ID")
  private Integer quotationConstraintId;

  @Temporal(TemporalType.DATE)
  @Column(name = "EFF_DATE", nullable = false)
  private Date effDate;

  @Temporal(TemporalType.DATE)
  @Column(name = "EXP_DATE")
  private Date expDate;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "STD_CODE", length = 30)
  private String stdCode;

  @Column(name = "IS_GLOBAL", length = 1)
  private String isGlobal;
}
