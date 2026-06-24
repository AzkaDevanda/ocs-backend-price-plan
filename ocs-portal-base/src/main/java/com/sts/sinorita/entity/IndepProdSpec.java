package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INDEP_PROD_SPEC")
@Entity
public class IndepProdSpec {
    @Id
    @Column(name = "INDEP_PROD_SPEC_ID", nullable = false)
    private Long indepProdSpecId;

    @Column(name = "SERV_TYPE")
    private Integer servType;

    @Column(name = "PAID_FLAG")
    private Character paidFlag;

    @Column(name = "BRAND_PRICE_PLAN_ID")
    private Long brandPricePlanId;

    @Column(name = "SP_ID")
    private Integer spId;
}
