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
public class CardProfileBonusId implements java.io.Serializable {
    private static final long serialVersionUID = 6300129420739136488L;
    @NotNull
    @Column(name = "CARD_PROFILE_ID", nullable = false)
    private Long cardProfileId;

    @NotNull
    @Column(name = "ACCT_RES_ID", nullable = false)
    private Long acctResId;

    @NotNull
    @Column(name = "PROD_SPEC_ID", nullable = false)
    private Long prodSpecId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CardProfileBonusId entity = (CardProfileBonusId) o;
        return Objects.equals(this.cardProfileId, entity.cardProfileId) &&
                Objects.equals(this.prodSpecId, entity.prodSpecId) &&
                Objects.equals(this.acctResId, entity.acctResId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardProfileId, prodSpecId, acctResId);
    }

}