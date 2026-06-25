package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.AcctServPriceLimitId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ACCT_SERV_PRICE_LIMIT", schema = "STS")
public class AcctServPriceLimit {

  @EmbeddedId
  private AcctServPriceLimitId id;

  @Column(name = "SUITABLE_TYPE", length = 1, nullable = false)
  private String suitableType;

  @Column(name = "SP_ID", precision = 6)
  private Long spId;

}
