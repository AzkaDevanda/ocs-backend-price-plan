package com.sts.sinorita.entity.embeddable;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimCardHisId implements Serializable {
  private Long simCardId;
  private Integer seq;
}
