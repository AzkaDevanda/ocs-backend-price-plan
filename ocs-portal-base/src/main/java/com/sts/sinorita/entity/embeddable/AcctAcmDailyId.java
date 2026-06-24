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
public class AcctAcmDailyId implements java.io.Serializable {
  private static final long serialVersionUID = 8574997130160157129L;
  @NotNull
  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @NotNull
  @Column(name = "RESOURCE_ID", nullable = false)
  private Integer resourceId;

  @NotNull
  @Column(name = "DATE_STAMP", nullable = false)
  private Integer dateStamp;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    AcctAcmDailyId entity = (AcctAcmDailyId) o;
    return Objects.equals(this.resourceId, entity.resourceId) &&
      Objects.equals(this.dateStamp, entity.dateStamp) &&
      Objects.equals(this.acctId, entity.acctId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(resourceId, dateStamp, acctId);
  }

}