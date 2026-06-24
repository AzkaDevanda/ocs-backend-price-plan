package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.DependProdPackageId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEPEND_PROD_PACKAGE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DependProdPackage {
  @EmbeddedId
  private DependProdPackageId id;

  @Column(name = "SEQ")
  private Integer seq;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "DEFAULT_FLAG", length = 1)
  private Character defaultFlag;
}
