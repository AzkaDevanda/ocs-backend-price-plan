package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.ReservePolicyId;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "RESERVE_POLICY")
public class ReservePolicy implements Serializable {
    @EmbeddedId
    private ReservePolicyId id;

    @Column(name = "VALUE", nullable = false)
    private Long value;

    @Column(name = "SP_ID")
    private Integer spId;

    @Lob
    @Column(name = "RULE_SCRIPT")
    private String ruleScript;
}
