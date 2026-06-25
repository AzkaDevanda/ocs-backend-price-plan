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
@Table(name = "SERVICE_OBJECT", schema = "STS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceObject {
  @Id
  @Column(name = "SERVICE_OBJECT_ID", nullable = false, precision = 6)
  private Long serviceObjectId;

  @Column(name = "SERVICE_RULE_TYPE", nullable = false, precision = 3)
  private Long serviceRuleType;

  @Column(name = "SERVICE_OBJECT_NAME", nullable = false, length = 255)
  private String serviceObjectName;

  /**
   * C = CLASS
   * S = SQL
   */
  @Column(name = "OBJECT_TYPE", nullable = false, length = 1)
  private String objectType;

  @Column(name = "CLASS_NAME", length = 255)
  private String className;

  @Column(name = "METHODE_NAME", length = 60)
  private String methodeName;

  @Column(name = "IS_PROD_FUCTION", nullable = false, length = 1)
  private String isProdFuction;

  @Column(name = "SQL_CONTENT", length = 1000)
  private String sqlContent;

  @Column(name = "SERVICE_NAME", length = 60)
  private String serviceName;

  @Column(name = "SP_ID", precision = 6)
  private Long spId;
}