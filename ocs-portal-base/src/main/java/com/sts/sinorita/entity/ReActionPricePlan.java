package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "RE_ACTION_PRICE_PLAN")
public class ReActionPricePlan implements Serializable {

    @Id
    @Column(name = "RE_ACTION_PRICE_PLAN_ID", nullable = false)
    private Integer id;

    @Column(name = "PERIOD_ID")
    private Integer periodId;

    @Column(name = "RE_ACTION_ID", nullable = false)
    private Integer reActionId;

    @Column(name = "PRICE_PLAN_ID")
    private Integer pricePlanId;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @Column(name = "STATE", nullable = false)
    private Character state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "PERIOD_REL_UNIT")
    private String periodRelUnit;

    @Column(name = "OPERATION_FLAG")
    private String operationFlag;

    @Column(name = "DEF_PERIOD")
    private String defPeriod;

    @Column(name = "SP_ID")
    private Integer spId;
}
