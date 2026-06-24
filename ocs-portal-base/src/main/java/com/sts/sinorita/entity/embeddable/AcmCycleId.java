package com.sts.sinorita.entity.embeddable;

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
public class AcmCycleId implements Serializable {

  @Column(name = "SUBS_ID", nullable = false)
  private Long subsId;

  @Column(name = "ACM_CYCLE_TYPE_ID", nullable = false)
  private Long acmCycleTypeId;

  @Column(name = "CYCLE_BEGIN_DATE", nullable = false)
  private Long cycleBeginDate;
}