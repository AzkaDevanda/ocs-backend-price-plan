package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "TIME_SPAN_UP", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSpanUp implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_span_up_id_seq")
  @SequenceGenerator(name = "time_span_up_id_seq", sequenceName = "TIME_SPAN_UP_ID_SEQ", allocationSize = 1)
  @Column(name = "TIME_SPAN_UP_ID", nullable = false)
  private Long timeSpanUpId;

  @Column(name = "UP_ID", nullable = false)
  private Long upId;

  @Column(name = "ADJUST_METHOD", length = 1, nullable = false)
  private String adjustMethod;

  @Column(name = "TIME_SPAN_ID", nullable = false)
  private Long timeSpanId;

  @Column(name = "RATE")
  private Long rate;

  @Column(name = "COMMENTS", length = 120)
  private String comments;

  @Column(name = "PRIORITY")
  private Long priority;

  @Column(name = "RUM")
  private Long rum;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "TIME_SPAN_UP_MODE", length = 1)
  private String timeSpanUpMode;
}