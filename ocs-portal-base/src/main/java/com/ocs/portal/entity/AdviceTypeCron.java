package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.AdviceTypeCronId;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ADVICE_TYPE_CRON", schema = "STS")
public class AdviceTypeCron {
  @EmbeddedId
  private AdviceTypeCronId id;

  @Column(name = "CRON_EXPRESSION", nullable = false, length = 120)
  private String cronExpression;

  @Column(name = "SEND_FLAG", nullable = false, length = 1)
  private String sendFlag;
}
