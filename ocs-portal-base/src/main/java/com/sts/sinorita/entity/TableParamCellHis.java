package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.sts.sinorita.entity.embeddable.TableParamCellHisId;

@Entity
@Table(name = "TABLE_PARAM_CELL_HIS", schema = "STS")
@IdClass(TableParamCellHisId.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableParamCellHis {

  @Id
  @Column(name = "CELL_ID", nullable = false)
  private Long cellId;

  @Id
  @Column(name = "TABLE_PARAM_ID", nullable = false)
  private Long tableParamId;

  @Id
  @Column(name = "PARAM_VER", nullable = false)
  private Integer paramVer;

  @Column(name = "COL_ID", nullable = false)
  private Long colId;

  @Column(name = "ZONE_ID", nullable = false)
  private Long zoneId;

  @Column(name = "CELL_VALUE", length = 60)
  private String cellValue;

  @Column(name = "DISP_CONTENT", length = 120)
  private String dispContent;

  @Column(name = "DISP_PREFIX", length = 120)
  private String dispPrefix;

  @Column(name = "DISP_SUFFIX", length = 120)
  private String dispSuffix;

  @Column(name = "VALUE_TYPE", length = 3)
  private String valueType;

  @Column(name = "RATE_PRECISION")
  private Integer ratePrecision;

  @Column(name = "LOCK_FLAG", length = 1)
  private String lockFlag;

  @Column(name = "COMMENTS", length = 255)
  private String comments;

  @Column(name = "STATE", length = 1, nullable = false)
  private String state;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDate stateDate;

  @Builder.Default
  @Column(name = "TEMPLATE_FLAG", length = 1)
  private String templateFlag = "N";

  @Column(name = "SRC_CELL_ID")
  private Long srcCellId;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "REC_EFF_DATE")
  private LocalDate recEffDate;
}
