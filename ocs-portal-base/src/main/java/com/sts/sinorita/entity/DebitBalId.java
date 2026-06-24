package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class DebitBalId implements java.io.Serializable {
  private static final long serialVersionUID = 4584876939128069157L;
  @NotNull
  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @NotNull
  @ColumnDefault("'1'")
  @Column(name = "LOAN_TYPE", nullable = false)
  private Boolean loanType = false;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    DebitBalId entity = (DebitBalId) o;
    return Objects.equals(this.loanType, entity.loanType) &&
      Objects.equals(this.acctId, entity.acctId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(loanType, acctId);
  }

}