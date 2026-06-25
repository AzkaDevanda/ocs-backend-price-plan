package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DYN_RE_ATTR")
public class DynReAttr {
  @Id
  @Column(name = "RE_ATTR", nullable = false)
  private Integer id;

  @Column(name = "DEPEND_PROD_SPEC_ID")
  private Integer dependProdSpecId;

  @NotNull
  @Column(name = "DYN_ATTR_ID", nullable = false)
  private Integer dynAttrId;

  @NotNull
  @Column(name = "ATTR_CATG", nullable = false)
  private Character attrCatg;

  @Column(name = "SP_ID")
  private Integer spId;

}