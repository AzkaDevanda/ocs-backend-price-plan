package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.EvaluateItemValueId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EVALUATE_ITEM_VALUE", schema = "STS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluateItemValue {

  @EmbeddedId
  private EvaluateItemValueId id;

  @Column(name = "VALUE", nullable = false)
  private String value;

  @Column(name = "VALUE_NAME", nullable = false)
  private String valueName;

  @Column(name = "VALUE_CODE")
  private String valueCode;

  @Column(name = "SP_ID")
  private Long spId;
}
