package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "ACC_NBR", schema = "STS", uniqueConstraints = { @UniqueConstraint(name = "AK_KEY_2_ACC_NBR", columnNames = { "ACC_NBR", "PREFIX" }) })
public class AccNbr {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_nbr_id_seq")
  @SequenceGenerator(name = "acc_nbr_id_seq", sequenceName = "ACC_NBR_ID_SEQ", allocationSize = 1)
  @Column(name = "ACC_NBR_ID", nullable = false)
  private Long accNbrId;

  @Column(name = "PREFIX", nullable = false, length = 60)
  private String prefix;

  @Column(name = "ACC_NBR", nullable = false, length = 60)
  private String accNbr;

  @Column(name = "STAFF_ID")
  private Long staffId;

  @Column(name = "ORG_ID")
  private Long orgId;

  @Column(name = "ACC_NBR_CLASS_ID")
  private Integer accNbrClassId;

  @Column(name = "ACC_NBR_TYPE_ID")
  private Integer accNbrTypeId;

  @Column(name = "ACC_NBR_STATE", nullable = false, length = 1)
  private String accNbrState;

  @Column(name = "HLR_ID")
  private Long hlrId;

  @Column(name = "NE_INFO", length = 255)
  private String neInfo;

  @Column(name = "AREA_ID")
  private Long areaId;

  @Column(name = "NBR_CLASS_JUDGE_ID")
  private Long nbrClassJudgeId;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "STATE_DATE", nullable = false)
  private Date stateDate;

  @Column(name = "COMMENTS", length = 4000)
  private String comments;

  @Column(name = "PPS_PWD", length = 120)
  private String ppsPwd;

  @Column(name = "PRE_CHARGING")
  private Long preCharging;

  @Column(name = "PEER_OPERATOR_CODE", length = 60)
  private String peerOperatorCode;

  @Column(name = "NP_AUTH_CODE", length = 60)
  private String npAuthCode;

  @Column(name = "IS_BINDING_FLAG", length = 1)
  private String isBindingFlag;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "PARTY_TYPE", length = 1)
  private String partyType;

  @Column(name = "PARTY_CODE", length = 60)
  private String partyCode;
}
