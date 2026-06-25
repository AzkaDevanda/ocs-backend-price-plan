package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "ACCT_PRICE_PLAN")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AcctPricePlan implements Serializable {

    @Id
    @Column(name = "PRICE_PLAN_ID", nullable = false)
    private Integer id;

    @Column(name = "PRICE_PLAN_TYPE", nullable = false)
    private Character pricePlanType;

    @Column(name = "SP_ID")
    private Integer spId;

}