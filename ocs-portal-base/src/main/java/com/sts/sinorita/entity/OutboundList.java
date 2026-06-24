package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "OUTBOUND_LIST", schema = "STS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboundList {
  @Id
  @Column(name = "ID", nullable = false, precision = 6)
  private Long id;

  @Column(name = "TYPE", nullable = false, length = 1)
  private String type;

  @Column(name = "VALUE", nullable = false, length = 60)
  private String value;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "UPDATE_DATE", nullable = false)
  private Date updateDate;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "PARTY_TYPE", length = 1)
  private String partyType;

  @Column(name = "PARTY_CODE", length = 60)
  private String partyCode;
}