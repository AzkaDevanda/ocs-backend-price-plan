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
public class ReserveDepositBalId implements java.io.Serializable {
  private static final long serialVersionUID = -5537360353778764559L;
  @NotNull
  @Column(name = "EVENT_INST_ID", nullable = false)
  private Long eventInstId;

  @NotNull
  @Column(name = "DEPOSIT_ITEM_ID", nullable = false)
  private Long depositItemId;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    ReserveDepositBalId entity = (ReserveDepositBalId) o;
    return Objects.equals(this.eventInstId, entity.eventInstId) &&
      Objects.equals(this.depositItemId, entity.depositItemId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(eventInstId, depositItemId);
  }

}