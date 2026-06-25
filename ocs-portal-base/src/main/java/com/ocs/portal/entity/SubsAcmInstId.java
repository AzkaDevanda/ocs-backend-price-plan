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
public class SubsAcmInstId implements java.io.Serializable {
  private static final long serialVersionUID = -6438844367439721437L;
  @NotNull
  @Column(name = "SUBS_ID", nullable = false)
  private Long subsId;

  @NotNull
  @Column(name = "RESOURCE_ID", nullable = false)
  private Integer resourceId;

  @NotNull
  @Column(name = "INST_ID", nullable = false)
  private Long instId;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    SubsAcmInstId entity = (SubsAcmInstId) o;
    return Objects.equals(this.instId, entity.instId) &&
      Objects.equals(this.subsId, entity.subsId) &&
      Objects.equals(this.resourceId, entity.resourceId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(instId, subsId, resourceId);
  }

}