package com.ocs.portal.entity.embeddable;

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
public class AcctAcmId implements java.io.Serializable {
  private static final long serialVersionUID = 6734136241768856268L;
  @NotNull
  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @NotNull
  @Column(name = "RESOURCE_ID", nullable = false)
  private Integer resourceId;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    AcctAcmId entity = (AcctAcmId) o;
    return Objects.equals(this.resourceId, entity.resourceId) &&
      Objects.equals(this.acctId, entity.acctId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(resourceId, acctId);
  }

}