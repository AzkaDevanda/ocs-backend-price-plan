package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "RE_USAGE")
public class ReUsage {

  @Id
  @Column(name = "RE_ID", nullable = false)
  private Integer id;

  @Column(name = "PARENT_RE_ID")
  private Long parentRe;

  @Column(name = "SP_ID")
  private Integer spId;

}