package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADJUST_REASON", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdjustReason {
  @Id
  @Column(name = "ADJUST_REASON_ID", nullable = false, precision = 3)
  private Long adjustReasonId;

  @Column(name = "ADJUST_REASON_NAME", nullable = false, length = 60)
  private String adjustReasonName;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "SP_ID", precision = 6)
  private Long spId;

  @Column(name = "ADJUST_REASON_CODE", length = 60)
  private String adjustReasonCode;

  @Column(name = "REASON_TYPE", length = 1)
  private String reasonType;
}
