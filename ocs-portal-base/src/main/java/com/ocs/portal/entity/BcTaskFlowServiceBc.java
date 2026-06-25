package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.BcTaskFlowServiceBcId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BC_TASK_FLOW_SERVICE_BC", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BcTaskFlowServiceBc {
  @EmbeddedId
  private BcTaskFlowServiceBcId id;

  @Column(name = "BC_ID", precision = 6, scale = 0)
  private Long bcId;

  @Column(name = "NEED_LOOP", length = 3)
  private String needLoop;

  @Column(name = "FORCE_MASTER", length = 3)
  private String forceMaster;

  @Column(name = "USED_TYPE", length = 1)
  private String usedType;
}
