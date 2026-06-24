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
public class CustAttrValueId implements Serializable {
  @Column(name = "CUST_ID", nullable = false, precision = 12, scale = 0)
  private Long custId;

  @Column(name = "ATTR_ID", nullable = false, precision = 6, scale = 0)
  private Long attrId;
}
