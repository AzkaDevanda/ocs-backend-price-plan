package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class PollTriggersId implements Serializable {
  @Column(name = "SCHED_NAME", length = 120, nullable = false)
  private String schedName;
  
  @Column(name = "TRIGGER_NAME", length = 80, nullable = false)
  private String triggerName;

  @Column(name = "TRIGGER_GROUP", length = 80, nullable = false)
  private String triggerGroup;
}