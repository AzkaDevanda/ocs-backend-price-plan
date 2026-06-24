package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "GL", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gl {
  @Id
  @Column(name = "GL_ID")
  private Long glId;

  @Column(name = "GL_TYPE_ID")
  private Long glTypeId;

  @Column(name = "GL_NAME", length = 60)
  private String glName;

  @Column(name = "PARENT_GL_ID")
  private Long parentGlId;

  @Column(name = "GL_CODE", length = 30)
  private String glCode;

  @Column(name = "COMMENTS", length = 120)
  private String comments;

  @Column(name = "COST_RATE", precision = 15, scale = 0)
  private BigDecimal costRate;

  @Column(name = "RATE_PRECISION", precision = 3, scale = 0)
  private Integer ratePrecision;

  @Column(name = "RUM", precision = 12, scale = 0)
  private BigDecimal rum;

  @Column(name = "RUM_TYPE", length = 1)
  private String rumType;

  @Column(name = "GL_PRIORITY", precision = 6, scale = 0)
  private Integer glPriority;

  @Column(name = "SP_ID")
  private Long spId;
}
