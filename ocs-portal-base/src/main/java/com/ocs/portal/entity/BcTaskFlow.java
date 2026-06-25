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
@Table(name = "BC_TASK_FLOW", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BcTaskFlow {
  @Id
  @Column(name = "TASK_ID", nullable = false, precision = 6, scale = 0)
  private Long taskId;

  @Column(name = "TASK_CODE", length = 100)
  private String taskCode;

  @Column(name = "TASK_NAME", nullable = false, length = 200)
  private String taskName;

  @Column(name = "COMMENTS", length = 4000)
  private String comments;

  @Column(name = "PARAM", length = 1000)
  private String param;

  @Column(name = "THREAD_NUM", precision = 9, scale = 0)
  private Long threadNum;

  @Column(name = "DISTRIBUTION_TYPE", precision = 3, scale = 0)
  private Integer distributionType;
}