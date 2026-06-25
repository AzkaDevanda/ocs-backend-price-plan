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
public class OverdueItemId implements java.io.Serializable {
  private static final long serialVersionUID = -7795306865233668206L;
  @NotNull
  @Column(name = "EVENT_INST_ID", nullable = false)
  private Long eventInstId;

  @NotNull
  @Column(name = "SEQ", nullable = false)
  private Integer seq;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    OverdueItemId entity = (OverdueItemId) o;
    return Objects.equals(this.eventInstId, entity.eventInstId) &&
      Objects.equals(this.seq, entity.seq);
  }

  @Override
  public int hashCode () {
    return Objects.hash(eventInstId, seq);
  }

}