package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "ACM_BENEFIT")
public class AcmBenefit implements Serializable {

    @EmbeddedId
    private AcmBenefitId id;

    @Column(name = "VALUE", nullable = false)
    private Integer value;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "TRIGGER_MODE")
    private Character triggerMode;


}