package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.AcctServProdLimitId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ACCT_SERV_PROD_LIMIT", schema = "STS")
public class AcctServProdLimit {

  @EmbeddedId
  private AcctServProdLimitId id;

  @Column(name = "SUITABLE_TYPE", length = 1, nullable = false)
  private String suitableType;

  @Column(name = "SP_ID", precision = 6)
  private Long spId;
}
