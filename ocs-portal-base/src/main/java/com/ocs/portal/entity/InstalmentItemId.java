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
public class InstalmentItemId implements java.io.Serializable {
    private static final long serialVersionUID = 3469336028530756210L;
    @NotNull
    @Column(name = "INSTALMENT_TYPE_ID", nullable = false)
    private Long instalmentTypeId;

    @NotNull
    @Column(name = "SEQ", nullable = false)
    private Integer seq;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InstalmentItemId entity = (InstalmentItemId) o;
        return Objects.equals(this.instalmentTypeId, entity.instalmentTypeId) &&
                Objects.equals(this.seq, entity.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instalmentTypeId, seq);
    }

}