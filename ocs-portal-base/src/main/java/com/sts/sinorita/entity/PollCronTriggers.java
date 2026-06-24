package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

import com.sts.sinorita.entity.embeddable.PollCronTriggersId;

@Entity
@Table(name = "POLL_CRON_TRIGGERS", schema = "STS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PollCronTriggers implements Serializable {
  @EmbeddedId
  private PollCronTriggersId id;

  @Column(name = "CRON_EXPRESSION", nullable = false, length = 80)
  private String cronExpression;

  @Column(name = "TIME_ZONE_ID", length = 80)
  private String timeZoneId;
}
