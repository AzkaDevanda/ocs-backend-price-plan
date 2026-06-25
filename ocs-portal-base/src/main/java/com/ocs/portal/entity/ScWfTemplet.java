package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SC_WF_TEMPLET", schema = "STS")
public class ScWfTemplet {
  @Id
  @Column(name = "WF_TEMPLET_ID")
  private Long wfTempletId;

  @Column(name = "WF_ACCESS_NBR_ID")
  private Long wfAccessNbrId;

  @Column(name = "WF_TEMPLET_NAME", length = 60)
  private String wfTempletName;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "EFF_DATE", nullable = false)
  private LocalDateTime effDate;

  @Column(name = "EXP_DATE")
  private LocalDateTime expDate;

  @Column(name = "TIME_LIMIT")
  private Long timeLimit;

  @Column(name = "COMMENTS", length = 120)
  private String comments;

  @Column(name = "IS_NEED_INST", length = 1)
  private String isNeedInst;

  @Column(name = "IS_CONTACT_LOG", length = 1)
  private String isContactLog;

  @Column(name = "TIMER")
  private Long timer;

  @Lob
  @Column(name = "FLOW_TEMPLATE_XML")
  private String flowTemplateXml;

  @Column(name = "SP_ID")
  private Long spId;
}
