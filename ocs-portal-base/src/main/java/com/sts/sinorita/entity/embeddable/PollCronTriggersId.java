package com.sts.sinorita.entity.embeddable;

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
public class PollCronTriggersId implements Serializable {
  @Column(name = "SCHED_NAME", length = 120, nullable = false)
  private String schedName;
  
  @Column(name = "TRIGGER_NAME", length = 80, nullable = false)
  private String triggerName;

  @Column(name = "TRIGGER_GROUP", length = 80, nullable = false)
  private String triggerGroup;
}