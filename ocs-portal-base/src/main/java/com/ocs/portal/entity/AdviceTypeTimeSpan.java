package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.ocs.portal.entity.embeddable.AdviceTypeTimeSpanId;

@Entity
@Table(name = "ADVICE_TYPE_TIME_SPAN", schema = "STS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdviceTypeTimeSpan {
  @EmbeddedId
  private AdviceTypeTimeSpanId id;

  @Column(name = "EFF_TIME")
  private LocalDateTime effTime;

  @Column(name = "EXP_TIME")
  private LocalDateTime expTime;

  @Column(name = "SP_ID", precision = 6)
  private Long spId;
}