package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.PollTriggersId;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "POLL_TRIGGERS", schema = "STS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollTriggers {
  @EmbeddedId
  private PollTriggersId id;

  @Column(name = "JOB_NAME", length = 80)
  private String jobName;

  @Column(name = "JOB_GROUP", length = 80)
  private String jobGroup;

  @Column(name = "IS_VOLATILE", length = 1, nullable = false)
  private String isVolatile;

  @Column(name = "DESCRIPTION", length = 120)
  private String description;

  @Column(name = "NEXT_FIRE_TIME")
  private Long nextFireTime;

  @Column(name = "PREV_FIRE_TIME")
  private Long prevFireTime;

  @Column(name = "PRIORITY")
  private Long priority;

  @Column(name = "TRIGGER_STATE", length = 16, nullable = false)
  private String triggerState;

  @Column(name = "TRIGGER_TYPE", length = 8, nullable = false)
  private String triggerType;

  @Column(name = "START_TIME", nullable = false)
  private Long startTime;

  @Column(name = "END_TIME")
  private Long endTime;

  @Column(name = "CALENDAR_NAME", length = 80)
  private String calendarName;

  @Column(name = "MISFIRE_INSTR")
  private Integer misfireInstr;

  @Lob
  @Column(name = "JOB_DATA")
  private byte[] jobData;
}
