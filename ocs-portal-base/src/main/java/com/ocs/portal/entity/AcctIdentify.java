package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ACCT_IDENTIFY", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcctIdentify {

  @Id
  @Column(name = "ACCT_IDENTIFY_ID", nullable = false, precision = 12)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acct_identify_seq_generator")
  @SequenceGenerator(name = "acct_identify_seq_generator", sequenceName = "ACCT_IDENTIFY_ID_SEQ", allocationSize = 1)
  private Long acctIdentifyId;

  @Column(name = "IDENTIFY_TYPE", nullable = false, length = 1)
  private String identifyType;

  @Column(name = "IDENTIFY_VALUE", nullable = false, length = 60)
  private String identifyValue;

  @Column(name = "ACCT_ID", nullable = false, precision = 12)
  private Long acctId;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "UPDATE_DATE")
  private LocalDateTime updateDate;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "ROUTING_ID", precision = 6)
  private Long routingId;

  @Column(name = "SP_ID", precision = 6)
  private Long spId;
}
