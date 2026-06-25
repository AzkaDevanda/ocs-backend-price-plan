package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PollJobDetailsId implements Serializable {
  @Column(name = "SCHED_NAME", length = 120, nullable = false)
  private String schedName;

  @Column(name = "JOB_NAME", length = 80, nullable = false)
  private String jobName;

  @Column(name = "JOB_GROUP", length = 80, nullable = false)
  private String jobGroup;
}