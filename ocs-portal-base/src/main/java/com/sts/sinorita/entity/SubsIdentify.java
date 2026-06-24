package com.sts.sinorita.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SUBS_IDENTIFY", schema = "STS")
public class SubsIdentify {

  @Id
  @Column(name = "SUBS_IDENTIFY_ID", nullable = false)
  private Long subsIdentifyId;

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

  @Column(name = "STATE", length = 1, nullable = false)
  private String state;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "ROUTING_ID")
  private Long routingId;
}
