package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "SC_WF_ACCESS_NBR", schema = "STS")
public class ScWfAccessNbr {
  @Id
  @Column(name = "WF_ACCESS_NBR_ID", nullable = false)
  private Long wfAccessNbrId;

  @Column(name = "CHANNEL_TYPE", nullable = false)
  private Integer channelType;

  @Column(name = "ACCESS_NBR", length = 30)
  private String accessNbr;

  @Column(name = "ERROR_ADVICE_TYPE")
  private Long errorAdviceType;
}
