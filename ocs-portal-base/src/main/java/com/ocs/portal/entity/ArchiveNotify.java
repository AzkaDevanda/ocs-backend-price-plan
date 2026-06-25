package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ARCHIVE_NOTIFY", schema = "STS")
public class ArchiveNotify {
  @Id
  @Column(name = "ARCHIVE_NOTIFY_ID", nullable = false)
  private Long archiveNotifyId;

  @Column(name = "SUBS_EVENT_ID", nullable = false)
  private Long subsEventId;

  @Column(name = "SEQ", nullable = false)
  private Integer seq;

  @Column(name = "ADVICE_TYPE")
  private Long adviceType;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "ADVICE_EVENT_ID")
  private Long adviceEventId;
}
