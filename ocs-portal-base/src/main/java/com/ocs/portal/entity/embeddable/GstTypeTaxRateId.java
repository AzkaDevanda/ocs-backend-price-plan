package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class GstTypeTaxRateId implements Serializable {
  @Column(name = "GST_TYPE", length = 1, nullable = false)
  private String gstType;

  @Column(name = "EFF_DATE", nullable = false)
  private LocalDateTime effDate;
}