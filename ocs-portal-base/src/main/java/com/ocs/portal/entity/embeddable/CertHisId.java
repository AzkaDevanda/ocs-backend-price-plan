package com.ocs.portal.entity.embeddable;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertHisId implements Serializable {
  @Column(name = "CERT_ID", nullable = false)
  private Long certId;

  @Column(name = "SEQ", nullable = false)
  private Long seq;

}
