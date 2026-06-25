package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.BundleUnitIndepProdSpecId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BUNDLE_UNIT_INDEP_PROD_SPEC", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BundleUnitIndepProdSpec {
  @EmbeddedId
  private BundleUnitIndepProdSpecId id;

  @Column(name = "SP_ID")
  private Integer spId;
}
