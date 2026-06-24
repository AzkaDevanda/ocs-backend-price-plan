package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GOODS_PROD_SPEC", schema = "STS")
public class GoodsProdSpec {
  @Id
  @Column(name = "GOODS_PROD_SPEC_ID", nullable = false)
  private Long id;

  @Column(name = "MODEL_ID")
  private Long modelId;

  @Column(name = "IS_PACKAGE", length = 1)
  private String isPackage;

  @Column(name = "INPUT_SALE_PRICE", length = 1)
  private String inputSalePrice;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "GROUP_TYPE", length = 1)
  private String groupType;

  @Column(name = "UPPER_LIMIT")
  private Integer upperLimit;

  @Column(name = "LOWER_LIMIT")
  private Integer lowerLimit;

  @Column(name = "SPU_ID")
  private Long spuId;

  @Column(name = "SALE_FLAG", length = 1)
  private String saleFlag;
}
