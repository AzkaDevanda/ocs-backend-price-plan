package com.sts.sinorita.entity;

import java.time.LocalDateTime;

import com.sts.sinorita.entity.embeddable.BalShareOperationHisId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "BAL_SHARE_OPERATION_HIS", schema = "STS")
public class BalShareOperationHis {

  @EmbeddedId
  private BalShareOperationHisId id;

  @Column(name = "SUBS_ID", nullable = false)
  private Long subsId;

  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @Column(name = "ACCT_RES_ID")
  private Long acctResId;

  @Column(name = "BAL_ID")
  private Long balId;

  @Column(name = "EFF_DATE", nullable = false)
  private LocalDateTime effDate;

  @Column(name = "EXP_DATE")
  private LocalDateTime expDate;

  @Column(name = "CEIL_LIMIT")
  private Long ceilLimit;

  @Column(name = "PRIORITY")
  private Long priority;

  @Column(name = "PAYMENT_FORCE", nullable = false)
  private String paymentForce;

  @Column(name = "DAILY_CEIL_LIMIT")
  private Long dailyCeilLimit;

  @Column(name = "SHARE_TYPE")
  private String shareType;

  @Column(name = "REC_CREATED_DATE")
  private LocalDateTime recCreatedDate;

  @Column(name = "BS_TEMPLET_ID")
  private Long bsTempletId;

  @Column(name = "PARTY_TYPE")
  private String partyType;

  @Column(name = "PARTY_CODE")
  private String partyCode;

  @Column(name = "OPERATION_TYPE")
  private String operationType;

  @Column(name = "COMMENTS")
  private String comments;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "ROUTING_ID")
  private Long routingId;

  @Column(name = "PART_ID", nullable = false)
  private Long partId;

}
