package com.ocs.portal.entity.mdbtt;

import com.ocs.portal.entity.embeddable.AcmCycleBfeId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACM_CYCLE_BFE", schema = "MDBTT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcmCycleBfe {

  @EmbeddedId
  private AcmCycleBfeId id;

  @Column(name = "CYCLE_BEGIN_DATE")
  private Long cycleBeginDate;

  @Column(name = "CYCLE_END_DATE")
  private Long cycleEndDate;

  @Column(name = "VALUE")
  private Long value;
}
