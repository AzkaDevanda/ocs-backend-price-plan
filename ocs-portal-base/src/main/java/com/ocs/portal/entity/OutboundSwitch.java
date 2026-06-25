package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "OUTBOUND_SWITCH", schema = "STS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboundSwitch {
  @Id
  @Column(name = "ID", nullable = false, precision = 6)
  private Long id;

  @Column(name = "VALUE", nullable = false, length = 1)
  private String value;

  @Column(name = "UPDATE_DATE", nullable = false)
  private LocalDateTime updateDate;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "PARTY_TYPE", length = 1)
  private String partyType;

  @Column(name = "PARTY_CODE", length = 60)
  private String partyCode;
}