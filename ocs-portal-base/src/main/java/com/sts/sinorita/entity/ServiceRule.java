package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.ServiceRuleId;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SERVICE_RULE", schema = "STS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRule {
  @EmbeddedId
  private ServiceRuleId id;

  @Column(name = "IS_PROD_FUCTION", nullable = false, length = 1)
  private String isProdFuction;

  @Column(name = "IS_READ", length = 1)
  private String isRead;

  @Column(name = "SERVICE_OBJECT_LIST", nullable = false, length = 255)
  private String serviceObjectList;

  @Column(name = "SP_ID", precision = 6)
  private Long spId;
}