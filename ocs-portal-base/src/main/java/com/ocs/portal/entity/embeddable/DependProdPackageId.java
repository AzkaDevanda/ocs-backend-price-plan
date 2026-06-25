package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DependProdPackageId implements Serializable {

  @Column(name = "DEPEND_PROD_PACKAGE_ID", nullable = false)
  private Integer dependProdPackageId;

  @Column(name = "MEM_DEPEND_PROD_SPEC_ID", nullable = false)
  private Integer memDependProdSpecId;
}
