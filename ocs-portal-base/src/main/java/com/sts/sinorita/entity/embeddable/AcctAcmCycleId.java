package com.sts.sinorita.entity.embeddable;

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
public class AcctAcmCycleId implements java.io.Serializable {
  private static final long serialVersionUID = 2099594261238675526L;
  @NotNull
  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @NotNull
  @Column(name = "RESOURCE_ID", nullable = false)
  private Integer resourceId;

  @NotNull
  @Column(name = "BILLING_CYCLE_ID", nullable = false)
  private Integer billingCycleId;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    AcctAcmCycleId entity = (AcctAcmCycleId) o;
    return Objects.equals(this.resourceId, entity.resourceId) &&
      Objects.equals(this.billingCycleId, entity.billingCycleId) &&
      Objects.equals(this.acctId, entity.acctId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(resourceId, billingCycleId, acctId);
  }

}