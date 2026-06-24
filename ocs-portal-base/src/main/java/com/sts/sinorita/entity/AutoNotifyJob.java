package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "AUTO_NOTIFY_JOB", schema = "STS")
public class AutoNotifyJob {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auto_notify_job_id_seq")
  @SequenceGenerator(name = "auto_notify_job_id_seq", sequenceName = "AUTO_NOTIFY_JOB_ID_SEQ", allocationSize = 1)
  @Column(name = "AUTO_NOTIFY_JOB_ID", nullable = false)
  private Long autoNotifyJobId;

  @Column(name = "AUTO_NOTIFY_JOB_NAME", nullable = false, length = 255)
  private String autoNotifyJobName;

  @Column(name = "ADVICE_TYPE")
  private Long adviceType;

  @Column(name = "NOTIFY_TYPE", nullable = false, length = 1)
  private String notifyType;

  @Column(name = "NOTIFY_DATE", nullable = false)
  private LocalDateTime notifyDate;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "NOTIFY_NUM")
  private Integer notifyNum;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "STAFF_ID", nullable = false)
  private Long staffId;

  @Column(name = "CURRENT_NOTIFY_NUM")
  private Long currentNotifyNum;

  @Column(name = "LAST_NOTIFY_TIME")
  private LocalDateTime lastNotifyTime;

  @Column(name = "SP_ID")
  private Long spId;
}
