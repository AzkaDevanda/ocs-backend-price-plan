package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "RATE_PLAN_PARAM")
public class RatePlanParam implements Serializable {
    @Id
    @Column(name = "RATE_PLAN_PARAM_ID", nullable = false)
    private Long ratePlanParamId;

    @Column(name = "RATE_PLAN_ID", nullable = false)
    private Long ratePlanId;

    @Column(name = "PARAM_TYPE", nullable = false, length = 1)
    private String paramType;

    @Column(name = "SIMPLE_PARAM_ID")
    private Long simpleParamId;

    @Column(name = "TABLE_PARAM_ID")
    private Long tableParamId;

    @Column(name = "SP_ID")
    private Long spId;
}
