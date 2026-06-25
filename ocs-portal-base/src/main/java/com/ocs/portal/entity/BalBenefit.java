package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "BAL_BENEFIT")
public class BalBenefit implements Serializable {

    @EmbeddedId
    private BalBenefitId id;

    @Column(name = "VALUE", nullable = false)
    private Long value;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "TRIGGER_MODE")
    private Boolean triggerMode;


}