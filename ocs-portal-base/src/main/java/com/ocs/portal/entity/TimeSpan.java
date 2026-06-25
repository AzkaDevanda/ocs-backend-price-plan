package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TIME_SPAN", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSpan {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_span_id_seq")
  @SequenceGenerator(name = "time_span_id_seq", sequenceName = "TIME_SPAN_ID_SEQ", allocationSize = 1)
  @Column(name = "TIME_SPAN_ID", nullable = false)
  private Long timeSpanId;

  @Column(name = "TIME_SPAN_NAME", nullable = false, length = 60)
  private String timeSpanName;

  @Column(name = "COMMENTS", length = 120)
  private String comments;

  @Column(name = "SP_ID")
  private Long spId;
}
