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
public class BalAcctItemTypeId implements java.io.Serializable {
  private static final long serialVersionUID = 2220490959746762255L;
  @NotNull
  @Column(name = "BAL_ID", nullable = false)
  private Long balId;

  @NotNull
  @Column(name = "ACCT_ITEM_TYPE_ID", nullable = false)
  private Integer acctItemTypeId;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    BalAcctItemTypeId entity = (BalAcctItemTypeId) o;
    return Objects.equals(this.acctItemTypeId, entity.acctItemTypeId) &&
            Objects.equals(this.balId, entity.balId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(acctItemTypeId, balId);
  }

}