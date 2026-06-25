package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRICE_PLAN")
public class PricePlan implements Serializable {

    @Id
    @Column(name = "PRICE_PLAN_ID")
    private Integer id; // set from offerID

    @Column(name = "APPLY_LEVEL", nullable = false)
    private Character applyLevel;

    @Column(name = "SERV_TYPE")
    private Integer servType;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "IS_PACKAGE")
    private Character isPackage;

    @Column(name = "WARN_LEVEL")
    private Character warnLevel;

    @Column(name = "POLICY_FLAG")
    private Character policyFlag;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "IS_TEMPLATE")
    private Character isTemplate;

    @Column(name = "SRC_PRICE_PLAN_ID")
    private Integer srcPricePlanId;

    @Column(name = "GROUP_TYPE")
    private Character groupType;

    @Column(name = "UPPER_LIMIT")
    private Integer upperLimit;

    @Column(name = "LOWER_LIMIT")
    private Integer lowerLimit;
}