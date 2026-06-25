package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SERVICE_RULE_TYPE", schema = "STS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRuleType {
  @Id
  @Column(name = "SERVICE_RULE_TYPE", nullable = false, precision = 3)
  private Long serviceRuleType;

  @Column(name = "SERVICE_RULE_TYPE_NAME", nullable = false, length = 60)
  private String serviceRuleTypeName;

  @Column(name = "COMMENTS", length = 255)
  private String comments;
}