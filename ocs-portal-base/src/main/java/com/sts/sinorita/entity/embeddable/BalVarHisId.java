package com.sts.sinorita.entity.embeddable;

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
public class BalVarHisId implements Serializable {
  @Column(name = "BAL_ID", nullable = false, precision = 18, scale = 0)
  private Long balId;

  @Column(name = "DATE_STAMP_START", nullable = false, precision = 12, scale = 0)
  private Long dateStampStart;
}
