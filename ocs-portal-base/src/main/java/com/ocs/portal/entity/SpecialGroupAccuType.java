package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SPECIAL_GROUP_ACCU_TYPE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialGroupAccuType {
  @Id
  @Column(name = "ACCU_TYPE_ID", precision = 6, scale = 0, nullable = false)
  private Long accuTypeId;

  @Column(name = "ACCU_TYPE_CODE", length = 30, nullable = false)
  private String accuTypeCode;

  @Column(name = "ACCU_TYPE_NAME", length = 255, nullable = false)
  private String accuTypeName;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;
}
