package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PaymentSettItemId implements java.io.Serializable {
  private static final long serialVersionUID = 5178498921595851874L;
  @NotNull
  @Column(name = "PAYMENT_SETT_ID", nullable = false)
  private Long paymentSettId;

  @NotNull
  @Column(name = "ACCT_ITEM_ID", nullable = false)
  private Long acctItemId;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    PaymentSettItemId entity = (PaymentSettItemId) o;
    return Objects.equals(this.acctItemId, entity.acctItemId) &&
      Objects.equals(this.paymentSettId, entity.paymentSettId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(acctItemId, paymentSettId);
  }

}