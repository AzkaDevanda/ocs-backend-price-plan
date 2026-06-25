package com.ocs.portal.entity;

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
public class ProdAttrValueId implements java.io.Serializable {
  private static final long serialVersionUID = -857738928327642247L;
  @NotNull
  @Column(name = "PROD_ID", nullable = false)
  private Long prodId;

  @NotNull
  @Column(name = "ATTR_ID", nullable = false)
  private Integer attrId;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    ProdAttrValueId entity = (ProdAttrValueId) o;
    return Objects.equals(this.attrId, entity.attrId) &&
            Objects.equals(this.prodId, entity.prodId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(attrId, prodId);
  }

}