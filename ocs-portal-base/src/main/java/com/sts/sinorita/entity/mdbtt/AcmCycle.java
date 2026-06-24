package com.sts.sinorita.entity.mdbtt;

import com.sts.sinorita.entity.embeddable.AcmCycleId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACM_CYCLE", schema = "MDBTT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcmCycle {

  @EmbeddedId
  private AcmCycleId id;

  @Column(name = "CYCLE_END_DATE")
  private Long cycleEndDate;

  @Column(name = "VALUE")
  private Long value;
}