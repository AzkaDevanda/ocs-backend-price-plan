package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "RE_TAX")
public class ReTax {
  @EmbeddedId
  private ReTaxId id;

  @Column(name = "SP_ID")
  private Integer spId;

}