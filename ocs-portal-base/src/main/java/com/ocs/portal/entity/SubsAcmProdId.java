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
public class SubsAcmProdId implements java.io.Serializable {
  private static final long serialVersionUID = 8091852025410414486L;
  @NotNull
  @Column(name = "SUBS_ID", nullable = false)
  private Long subsId;

  @NotNull
  @Column(name = "RESOURCE_ID", nullable = false)
  private Integer resourceId;

  @NotNull
  @Column(name = "PROD_ID", nullable = false)
  private Long prodId;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    SubsAcmProdId entity = (SubsAcmProdId) o;
    return Objects.equals(this.subsId, entity.subsId) &&
      Objects.equals(this.resourceId, entity.resourceId) &&
      Objects.equals(this.prodId, entity.prodId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(subsId, resourceId, prodId);
  }

}