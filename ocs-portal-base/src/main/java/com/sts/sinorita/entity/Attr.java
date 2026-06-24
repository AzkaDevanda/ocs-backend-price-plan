package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Data
@Entity
@Table(name = "ATTR")
public class Attr implements Serializable {
  @Id
  @Column(name = "ATTR_ID", nullable = false)
  private Integer id;

  @Column(name = "ATTR_NAME", nullable = false, length = 60)
  private String attrName;

  @Column(name = "ATTR_TYPE", nullable = false)
  private Character attrType;

  @Column(name = "OBJ_ATTR_ID")
  private Integer objAttrId;

  @Column(name = "ATTR_CODE", length = 30)
  private String attrCode;

  @ColumnDefault("'Y'")
  @Column(name = "CONFIG_VISIBLE", nullable = false)
  private Character configVisible;

  @Column(name = "CSR_VISIBLE", nullable = false)
  private Character csrVisible;

  @Column(name = "INSTANTIATABLE")
  private Character instantiatable;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "EDITABLE")
  private Character editable;

  //test

}