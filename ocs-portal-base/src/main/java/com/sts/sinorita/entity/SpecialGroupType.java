package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SPECIAL_GROUP_TYPE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialGroupType {
  @Id
  @Column(name = "SPECIAL_GROUP_TYPE_ID", precision = 6, scale = 0, nullable = false)
  private Long specialGroupTypeId;

  @Column(name = "SPECIAL_GROUP_TYPE_CODE", length = 30, nullable = false)
  private String specialGroupTypeCode;

  @Column(name = "SPECIAL_GROUP_TYPE_NAME", length = 255, nullable = false)
  private String specialGroupTypeName;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "IS_BLACK", length = 1)
  private String isBlack;
}