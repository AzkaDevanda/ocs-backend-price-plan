package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.GoodsProdPackageId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GOODS_PROD_PACKAGE", schema = " STS")
public class GoodsProdPackage {
  @EmbeddedId
  private GoodsProdPackageId id;

  @Column(name = "QUANTITY")
  private Integer quantity;

  @Column(name = "SALE_LIST_PRICE")
  private Long saleListPrice;

  @Column(name = "SEQ")
  private Long seq;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "DEFAULT_FLAG", length = 1)
  private String defaultFlag;
}
