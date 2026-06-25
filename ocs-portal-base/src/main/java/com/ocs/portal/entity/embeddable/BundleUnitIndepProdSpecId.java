package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BundleUnitIndepProdSpecId {
  private Long bundleUnitId;
  private Long indepProdSpecId;
}
