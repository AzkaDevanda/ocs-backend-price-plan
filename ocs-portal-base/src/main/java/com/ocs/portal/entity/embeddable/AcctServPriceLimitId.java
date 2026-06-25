package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class AcctServPriceLimitId {
  @Column(name = "ACCT_BOOK_TYPE", length = 1, nullable = false)
  private String acctBookType;

  @Column(name = "PRICE_PLAN_ID", precision = 9, nullable = false)
  private Long pricePlanId;
}
