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
public class DebitBalInstallId implements java.io.Serializable {
  private static final long serialVersionUID = 5647186281682385484L;
  @NotNull
  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @NotNull
  @Column(name = "LOAN_TYPE", nullable = false)
  private Boolean loanType = false;

  @NotNull
  @Column(name = "INSTALL_SEQ", nullable = false)
  private Short installSeq;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    DebitBalInstallId entity = (DebitBalInstallId) o;
    return Objects.equals(this.loanType, entity.loanType) &&
      Objects.equals(this.acctId, entity.acctId) &&
      Objects.equals(this.installSeq, entity.installSeq);
  }

  @Override
  public int hashCode () {
    return Objects.hash(loanType, acctId, installSeq);
  }

}