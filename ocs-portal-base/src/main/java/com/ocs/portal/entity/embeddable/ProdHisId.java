package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdHisId implements Serializable {
  @Column(name = "PROD_ID", nullable = false, precision = 12, scale = 0)
  private Long prodId;

  @Column(name = "SEQ", nullable = false, precision = 6, scale = 0)
  private Long seq;
}