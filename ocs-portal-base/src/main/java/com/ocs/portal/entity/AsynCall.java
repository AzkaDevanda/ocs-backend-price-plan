package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ASYN_CALL", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsynCall {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asyn_call_id_seq")
  @SequenceGenerator(name = "asyn_call_id_seq", sequenceName = "ASYN_CALL_ID_SEQ", allocationSize = 1)
  @Column(name = "ASYN_CAL_ID", nullable = false)
  private Long asynCalId;

  @Column(name = "SUBS_ID", nullable = false)
  private Long subsId;

  @Column(name = "AVP", length = 4000)
  private String avp;

  @Column(name = "EVENT_ID")
  private Long eventId;

  @Column(name = "CREATED_DATE")
  private LocalDateTime createdDate;

  @Column(name = "STATE", length = 1)
  private String state;

  @Column(name = "STATE_DATE")
  private LocalDateTime stateDate;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "PRIORITY")
  private Integer priority;
}