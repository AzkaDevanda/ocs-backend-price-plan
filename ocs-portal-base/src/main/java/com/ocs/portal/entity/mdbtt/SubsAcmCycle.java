package com.ocs.portal.entity.mdbtt;

import com.ocs.portal.entity.embeddable.SubsAcmCycleId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SUBS_ACM_CYCLE", schema = "MDBTT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubsAcmCycle {
  @EmbeddedId
  private SubsAcmCycleId id;

  @Column(name = "VALUE")
  private Long value;
}
