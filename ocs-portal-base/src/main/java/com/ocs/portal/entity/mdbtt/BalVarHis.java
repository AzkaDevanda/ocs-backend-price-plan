package com.ocs.portal.entity.mdbtt;

import com.ocs.portal.entity.embeddable.BalVarHisId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BAL_VAR_HIS", schema = "MDBTT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalVarHis {
  @EmbeddedId
  private BalVarHisId id;

  @Column(name = "DATE_STAMP_FINISH", precision = 12, scale = 0)
  private Long dateStampFinish;

  @Column(name = "VAR_BAL", precision = 20, scale = 0)
  private Long varBal;
}