package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SYSTEM_PARAM", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemParam {
  @Id
  @Column(name = "PARAM", nullable = false, precision = 6)
  private Long param;

  @Column(name = "PARAM_NAME", nullable = false, length = 128)
  private String paramName;

  @Column(name = "CURRENT_VALUE", length = 4000)
  private String currentValue;

  @Column(name = "COMMENTS", length = 4000)
  private String comments;

  @Column(name = "MASK", nullable = false, length = 60)
  private String mask;
}
