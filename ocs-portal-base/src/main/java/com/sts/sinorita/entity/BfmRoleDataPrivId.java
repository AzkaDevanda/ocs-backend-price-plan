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
public class BfmRoleDataPrivId implements java.io.Serializable {
  private static final long serialVersionUID = 3244584714160791984L;
  @NotNull
  @Column(name = "DATA_PRIV_ID", nullable = false)
  private Integer dataPrivId;

  @NotNull
  @Column(name = "ROLE_ID", nullable = false)
  private Integer roleId;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    BfmRoleDataPrivId entity = (BfmRoleDataPrivId) o;
    return Objects.equals(this.roleId, entity.roleId) &&
            Objects.equals(this.dataPrivId, entity.dataPrivId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(roleId, dataPrivId);
  }

}