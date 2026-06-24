package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "SUBS_PRICE_PLAN")
public class SubsPricePlan implements Serializable {

    // FK
    // PricePlan
    @Id
    @Column(name = "PRICE_PLAN_ID", nullable = false)
    private Integer id;

    @Column(name = "PRICE_PLAN_TYPE", nullable = false)
    private Character pricePlanType;

    @Column(name = "SP_ID")
    private Integer spId;

}