package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SIM_TYPE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimType {
  @Id
  @Column(name = "SIM_TYPE_ID", nullable = false)
  private Long simTypeId;

  @Column(name = "SIM_TYPE_NAME", nullable = false, length = 60)
  private String simTypeName;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "SIM_TYPE_CODE", length = 60)
  private String simTypeCode;
}
