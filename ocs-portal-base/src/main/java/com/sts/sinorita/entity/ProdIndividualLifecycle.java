package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PROD_INDIVIDUAL_LIFECYCLE", schema = "STS", indexes = {
    @Index(name = "IDX_PIL_PROD_ID", columnList = "PROD_ID")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdIndividualLifecycle {
  @Id
  @Column(name = "PROD_IND_LIFECYCLE_ID", nullable = false, precision = 12, scale = 0)
  private Long prodIndLifecycleId;

  @Column(name = "PROD_ID", precision = 12, scale = 0)
  private Long prodId;

  @Column(name = "PROD_STATE", length = 1)
  private String prodState;

  @Column(name = "PROD_STATE_DATE")
  private LocalDateTime prodStateDate;

  @Column(name = "STATE", length = 1)
  private String state;

  @Column(name = "CREATED_DATE")
  private LocalDateTime createdDate;

  @Column(name = "STATE_DATE")
  private LocalDateTime stateDate;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;

  @Column(name = "ROUTING_ID", precision = 6, scale = 0)
  private Long routingId;

}
