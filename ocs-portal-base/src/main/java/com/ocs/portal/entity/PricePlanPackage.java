package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRICE_PLAN_PACKAGE")
public class PricePlanPackage {

    @Id
    @Column(name = "PRICE_PLAN_PACKAGE_ID")
    private Integer id;

    @Column(name = "MEM_PRICE_PLAN_ID")
    private Integer memPricePlanId;

    @Column(name = "SEQ")
    private Integer seq;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "DEFAULT_FLAG")
    private Character defaultFlag;


}
