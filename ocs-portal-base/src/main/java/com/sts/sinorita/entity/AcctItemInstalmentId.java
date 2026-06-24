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
public class AcctItemInstalmentId implements java.io.Serializable {
  private static final long serialVersionUID = -4640512519455023695L;
  @NotNull
  @Column(name = "INSTLMENT_ID", nullable = false)
  private Long instlmentId;

  @NotNull
  @Column(name = "SEQ", nullable = false)
  private Integer seq;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    AcctItemInstalmentId entity = (AcctItemInstalmentId) o;
    return Objects.equals(this.instlmentId, entity.instlmentId) &&
      Objects.equals(this.seq, entity.seq);
  }

  @Override
  public int hashCode () {
    return Objects.hash(instlmentId, seq);
  }

}