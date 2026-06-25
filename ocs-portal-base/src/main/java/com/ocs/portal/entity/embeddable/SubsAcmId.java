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
public class SubsAcmId implements java.io.Serializable {
  private static final long serialVersionUID = 8646961514693013882L;
  @NotNull
  @Column(name = "SUBS_ID", nullable = false)
  private Long subsId;

  @NotNull
  @Column(name = "RESOURCE_ID", nullable = false)
  private Integer resourceId;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    SubsAcmId entity = (SubsAcmId) o;
    return Objects.equals(this.subsId, entity.subsId) &&
      Objects.equals(this.resourceId, entity.resourceId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(subsId, resourceId);
  }

}