package com.ocs.portal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "BSD_WORK_HOUR", schema = "STS")
public class BsdWorkHour {

  @Id
  @Column(name = "BSD_WORK_HOUR_ID")
  private Long bsdWorkHourId;

  @Column(name = "BAL_SHARE_DETAIL_ID", nullable = false)
  private Long balShareDetailId;

  @Column(name = "TIME_SPAN_ID", nullable = false)
  private Long timeSpanId;

  @Column(name = "TIME_TYPE", nullable = false, length = 1)
  private String timeType;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDateTime stateDate;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "SP_ID")
  private Long spId;

}
