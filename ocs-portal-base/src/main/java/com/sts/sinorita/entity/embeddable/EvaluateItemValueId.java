package com.sts.sinorita.entity.embeddable;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class EvaluateItemValueId implements Serializable {

  @Column(name = "EVALUATE_ITEM_ID")
  private Long evaluateItemId;

  @Column(name = "SEQ")
  private Integer seq;
}
