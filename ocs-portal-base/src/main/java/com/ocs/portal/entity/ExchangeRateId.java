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
public class ExchangeRateId implements java.io.Serializable {
    private static final long serialVersionUID = -8201791780710493962L;
    @NotNull
    @Column(name = "SRC_ACCT_RES_ID", nullable = false)
    private Long srcAcctResId;

    @NotNull
    @Column(name = "OBJ_ACCT_RES_ID", nullable = false)
    private Long objAcctResId;

    @NotNull
    @Column(name = "SEQ", nullable = false)
    private Long seq;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExchangeRateId entity = (ExchangeRateId) o;
        return Objects.equals(this.srcAcctResId, entity.srcAcctResId) &&
                Objects.equals(this.seq, entity.seq) &&
                Objects.equals(this.objAcctResId, entity.objAcctResId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(srcAcctResId, seq, objAcctResId);
    }

}