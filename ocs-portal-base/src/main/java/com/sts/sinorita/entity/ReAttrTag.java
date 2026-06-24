package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "RE_ATTR_TAG")
public class ReAttrTag {
  @Id
  @Column(name = "RE_ATTR", nullable = false)
  private Integer id;

  @Column(name = "TAG", nullable = false)
  private String tag;

  @Column(name = "SP_ID")
  private Integer spId;

}