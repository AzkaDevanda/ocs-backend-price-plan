package com.ocs.portal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BAL_SHARE_DETAIL", schema = "STS")
public class BalShareDetail {

  @Id
  @Column(name = "BAL_SHARE_DETAIL_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bal_share_detail_seq_generator")
  @SequenceGenerator(name = "bal_share_detail_seq_generator", sequenceName = "BAL_SHARE_DETAIL_ID_SEQ", allocationSize = 1)
  private Long balShareDetailId;

  @Column(name = "BAL_SHARE_ID", nullable = false)
  private Long balShareId;

  @Column(name = "SUBS_ID", nullable = false)
  private Long subsId;

  @Column(name = "ACCT_RES_ID")
  private Long acctResId;

  @Column(name = "BAL_ID")
  private Long balId;

  @Column(name = "EFF_DATE", nullable = false)
  private LocalDateTime effDate;

  @Column(name = "EXP_DATE")
  private LocalDateTime expDate;

  @Column(name = "PRIORITY")
  private Long priority;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "ROUTING_ID")
  private Long routingId;

  @Column(name = "BAL_TYPE")
  private Integer balType;

  @Column(name = "LIMIT_SUBS")
  private String limitSubs;

  @Column(name = "EXC_ACCT_RES_IDS")
  private String excAcctResIds;

  @Column(name = "BS_TYPE_ID")
  private Long bsTypeId;

  @Column(name = "PROD_ID")
  private Long prodId;

  @Column(name = "SUBS_UPP_INST_ID")
  private Long subsUppInstId;

}
