package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CARD_PROFILE_BONUS")
public class CardProfileBonus {
    @EmbeddedId
    private CardProfileBonusId id;

    @NotNull
    @Column(name = "CHARGE", nullable = false)
    private Long charge;

    @Column(name = "SECONDS")
    private Long seconds;

    @Column(name = "PROLONG_POLICY")
    private Character prolongPolicy;

}