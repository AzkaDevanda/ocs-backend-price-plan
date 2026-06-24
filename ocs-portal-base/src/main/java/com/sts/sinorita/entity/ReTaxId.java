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
public class ReTaxId implements java.io.Serializable {
  private static final long serialVersionUID = 8842541286604021459L;
  @NotNull
  @Column(name = "RE_ID", nullable = false)
  private Integer reId;

  @NotNull
  @Column(name = "TAX_ID", nullable = false)
  private Integer taxId;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    ReTaxId entity = (ReTaxId) o;
    return Objects.equals(this.taxId, entity.taxId) &&
      Objects.equals(this.reId, entity.reId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(taxId, reId);
  }

}