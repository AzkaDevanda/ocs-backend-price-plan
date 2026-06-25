package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TEXT_ATTR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextAttr {
  @Id
  // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "text_attr_id_seq")
  @Column(name = "TEXT_ATTR_ID", nullable = false)
  private Integer textAttrId;

  @Column(name = "DATA_TYPE", length = 1)
  private Character dataType;

  @Column(name = "MASK", length = 4000)
  private String mask;

  @Column(name = "RULE_SCRIPT", length = 4000)
  private String ruleScript;

  @Column(name = "EDITABLE", length = 1)
  private Character editable;

  @Column(name = "EXCEPTION_MESSAGE", length = 60, nullable = false)
  private String exceptionMessage;

  @Column(name = "MIN_VALUE", length = 60)
  private String minValue;

  @Column(name = "MAX_VALUE", length = 60)
  private String maxValue;

  @Column(name = "SP_ID")
  private Integer spId;
}