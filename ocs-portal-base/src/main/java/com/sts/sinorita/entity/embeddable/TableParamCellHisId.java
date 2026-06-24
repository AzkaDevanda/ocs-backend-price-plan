package com.sts.sinorita.entity.embeddable;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TableParamCellHisId implements Serializable {
  private Long cellId;
  private Long tableParamId;
  private Integer paramVer;
}
