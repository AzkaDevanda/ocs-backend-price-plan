package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ASYN_CALL_TO_BILLING", schema = "STS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsynCallToBilling {
  @Id
  @Column(name = "ID", precision = 12, scale = 0, nullable = false)
  private Long id;

  @Column(name = "EVENT", precision = 9, scale = 0, nullable = false)
  private Long event;

  @Column(name = "\"INPUT\"", length = 1024, nullable = false)
  private String input;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "ERROR_MSG", length = 2000)
  private String errorMsg;

  @Column(name = "STATE", length = 1, nullable = false)
  private String state;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDateTime stateDate;

  @Column(name = "ACCT_ID", precision = 12, scale = 0, nullable = false)
  private Long acctId;

  @Column(name = "ROUTING_ID", precision = 6, scale = 0)
  private Long routingId;
}