package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "SERVICE_BC", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceBc {
  @Id
  @Column(name = "BC_ID")
  private Long bcId;

  @Column(name = "BC_NAME", nullable = false)
  private String bcName;

  @Column(name = "INPUT_PARAM")
  private String inputParam;

  @Column(name = "OUTPUT_PARAM")
  private String outputParam;

  @Column(name = "COMMENTS")
  private String comments;

  @Column(name = "OBJ_TYPE", nullable = false)
  private String objType;

  @Column(name = "CLASS_NAME")
  private String className;

  @Column(name = "METHOD_NAME")
  private String methodName;

  @Column(name = "SQL_CONTENT")
  private String sqlContent;

  @Column(name = "OBJECT_CATG")
  private String objectCatg;

  @Column(name = "SERVICE_RULE_TYPE")
  private Long serviceRuleType;

  @Column(name = "SERVICE_NAME")
  private String serviceName;

  @Column(name = "JYTHON_SCRIPT")
  private String jythonScript;

  @Column(name = "DATABASE")
  private String database;

  @Column(name = "CAT_CODE")
  private String catCode;

  @Column(name = "SQL_PAGE_NUMBER")
  private Long sqlPageNumber;

  @Column(name = "SQL_PAGE_ORDER")
  private String sqlPageOrder;

  @Column(name = "UPDATE_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime updateDate;
}