package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SUBS", schema = "STS")
public class Subs {

  @Id
  @Column(name = "SUBS_ID", nullable = false, precision = 12, scale = 0)
  private Long subsId;

  @Column(name = "PREFIX", nullable = false, length = 60)
  private String prefix;

  @Column(name = "ACC_NBR", nullable = false, length = 60)
  private String accNbr;

  @Column(name = "ACCT_ID", nullable = false, precision = 12, scale = 0)
  private Long acctId;

  @Column(name = "AREA_ID", nullable = false, precision = 6, scale = 0)
  private Long areaId;

  @Column(name = "AGENT_ID", precision = 6, scale = 0)
  private Long agentId;

  @Column(name = "CUST_ID", precision = 12, scale = 0)
  private Long custId;

  @Column(name = "USER_ID", precision = 12, scale = 0)
  private Long userId;

  @Column(name = "ORG_ID", precision = 6, scale = 0)
  private Long orgId;

  @Column(name = "UPDATE_DATE", nullable = false)
  private LocalDateTime updateDate;

  @Column(name = "DEF_LANG_ID", precision = 6, scale = 0)
  private Long defLangId;

  @Column(name = "PPS_PWD", length = 120)
  private String ppsPwd;

  @Column(name = "COMMENTS", length = 4000)
  private String comments;

  @Column(name = "PPS_CREDIT_LIMIT", precision = 12, scale = 0)
  private Long ppsCreditLimit;

  @Column(name = "NEED_UPLOAD", length = 1)
  private String needUpload;     // default 'N'

  @Column(name = "ROUTING_ID", precision = 6, scale = 0)
  private Long routingId;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;

  @Column(name = "UPLOAD_TYPE", length = 1)
  private String uploadType;

  @Column(name = "CREATE_STAFF_ID", precision = 6, scale = 0)
  private Long createStaffId;

  @Column(name = "SECOND_NBR", length = 60)
  private String secondNbr;

  @Transient
  private Prod prod;

  @Transient
  private Long defaultPricePlanId;


}
