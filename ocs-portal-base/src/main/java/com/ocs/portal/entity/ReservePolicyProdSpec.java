package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.ReservePolicyProdSpecId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "RESERVE_POLICY_PROD_SPEC")
public class ReservePolicyProdSpec {
    @EmbeddedId
    private ReservePolicyProdSpecId id;

    @Column(name = "VALUE", nullable = false)
    private Long value;

    @Column(name = "SP_ID")
    private Integer spId;

}
