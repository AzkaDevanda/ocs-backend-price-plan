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
public class BalShareHisId implements java.io.Serializable {
  private static final long serialVersionUID = 8943390691995988291L;
  @NotNull
  @Column(name = "BAL_SHARE_ID", nullable = false)
  private Long balShareId;

  @NotNull
  @Column(name = "DATE_STAMP", nullable = false)
  private Long dateStamp;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    BalShareHisId entity = (BalShareHisId) o;
    return Objects.equals(this.dateStamp, entity.dateStamp) &&
      Objects.equals(this.balShareId, entity.balShareId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(dateStamp, balShareId);
  }

}