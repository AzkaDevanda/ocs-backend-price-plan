package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "RESERVE_RE_ATTR")
public class ReserveReAttr {
  @Id
  @Column(name = "RE_ATTR", nullable = false)
  private Integer id;

  @Size(max = 60)
  @NotNull
  @Column(name = "RE_ATTR_NAME", nullable = false, length = 60)
  private String reAttrName;

  @Size(max = 2000)
  @Column(name = "COMMENTS", length = 2000)
  private String comments;

  @NotNull
  @Column(name = "MEASURABLE", nullable = false)
  private Character measurable;

  @Size(max = 60)
  @Column(name = "MASK", length = 60)
  private String mask;

  @Size(max = 4000)
  @Column(name = "LOOKUP_SCRIPT", length = 4000)
  private String lookupScript;

}