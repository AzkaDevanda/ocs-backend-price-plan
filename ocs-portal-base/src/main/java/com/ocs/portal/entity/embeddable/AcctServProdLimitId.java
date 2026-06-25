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
public class AcctServProdLimitId {
  @Column(name = "ACCT_BOOK_TYPE", length = 1, nullable = false)
  private String acctBookType;

  @Column(name = "INDEP_PROD_SPEC_ID", precision = 9, nullable = false)
  private Long indepProdSpecId;

}
