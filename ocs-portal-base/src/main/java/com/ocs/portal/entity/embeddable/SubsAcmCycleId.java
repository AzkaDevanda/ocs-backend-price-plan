package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubsAcmCycleId implements Serializable {

  @Column(name = "SUBS_ID", nullable = false)
  private Long subsId;

  @Column(name = "RESOURCE_ID", nullable = false)
  private Long resourceId;

  @Column(name = "BILLING_CYCLE_ID", nullable = false)
  private Long billingCycleId;
}
