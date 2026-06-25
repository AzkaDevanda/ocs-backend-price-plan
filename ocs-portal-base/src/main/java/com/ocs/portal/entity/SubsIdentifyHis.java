package com.ocs.portal.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.ocs.portal.entity.embeddable.SubsIdentifyHisId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@IdClass(SubsIdentifyHisId.class)
@Table(name = "SUBS_IDENTIFY_HIS", schema = "STS")
public class SubsIdentifyHis implements Serializable {

  @Id
  @Column(name = "SUBS_IDENTIFY_ID", nullable = false)
  private Long subsIdentifyId;

  @Id
  @Column(name = "SEQ", nullable = false)
  private Long seq;

  @Column(name = "IDENTIFY_TYPE", length = 1)
  private String identifyType;

  @Column(name = "IDENTIFY_VALUE", length = 60, nullable = false)
  private String identifyValue;

  @Column(name = "SUBS_ID", nullable = false)
  private Long subsId;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "UPDATE_DATE")
  private LocalDateTime updateDate;

  @Column(name = "REC_EFF_DATE")
  private LocalDateTime recEffDate;

  @Column(name = "REC_EXP_DATE")
  private LocalDateTime recExpDate;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "ROUTING_ID")
  private Long routingId;
}
