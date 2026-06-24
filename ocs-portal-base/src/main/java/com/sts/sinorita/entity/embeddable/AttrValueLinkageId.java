package com.sts.sinorita.entity.embeddable;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttrValueLinkageId implements Serializable {
  @Column(name = "BASE_ATTR_ID", nullable = false)
  private Long baseAttrId;

  @Column(name = "ATTR_VALUE_ID", nullable = false)
  private Long attrValueId;

  @Column(name = "PARENT_ATTR_ID", nullable = false)
  private Long parentAttrId;

  @Column(name = "PARENT_ATTR_VALUE_ID", nullable = false)
  private Long parentAttrValueId;
}