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
public class NotePayPaymentId implements java.io.Serializable {
  private static final long serialVersionUID = -5772698119915102637L;
  @NotNull
  @Column(name = "PAYMENT_ID", nullable = false)
  private Long paymentId;

  @NotNull
  @Column(name = "NOTE_ID", nullable = false)
  private Long noteId;

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    NotePayPaymentId entity = (NotePayPaymentId) o;
    return Objects.equals(this.paymentId, entity.paymentId) &&
      Objects.equals(this.noteId, entity.noteId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(paymentId, noteId);
  }

}