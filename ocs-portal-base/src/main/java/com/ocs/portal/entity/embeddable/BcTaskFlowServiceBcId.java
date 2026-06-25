package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BcTaskFlowServiceBcId implements Serializable {
  @Column(name = "TASK_ID", nullable = false, precision = 6, scale = 0)
  private Long taskId;

  @Column(name = "SEQ", nullable = false, precision = 3, scale = 0)
  private Integer seq;
}