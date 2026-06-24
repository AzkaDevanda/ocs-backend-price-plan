package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ADVICE_EVENT")
public class AdviceExclution {

  @Id
  @Column(name = "ADVICE_TYPE")
  private Integer adviceType;

}
