package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

import com.sts.sinorita.entity.embeddable.PollJobDetailsId;

@Entity
@Table(name = "POLL_JOB_DETAILS", schema = "STS", indexes = {
    @Index(name = "IDX_POLL_J_REQ_RECOVERY", columnList = "REQUESTS_RECOVERY")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PollJobDetails implements Serializable {
  @EmbeddedId
  private PollJobDetailsId id;

  @Column(name = "DESCRIPTION", length = 120)
  private String description;

  @Column(name = "JOB_CLASS_NAME", nullable = false, length = 128)
  private String jobClassName;

  @Column(name = "IS_DURABLE", nullable = false, length = 1)
  private String isDurable;

  @Column(name = "IS_VOLATILE", nullable = false, length = 1)
  private String isVolatile;

  @Column(name = "IS_STATEFUL", nullable = false, length = 1)
  private String isStateful;

  @Column(name = "REQUESTS_RECOVERY", nullable = false, length = 1)
  private String requestsRecovery;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "JOB_DATA")
  private byte[] jobData;
}