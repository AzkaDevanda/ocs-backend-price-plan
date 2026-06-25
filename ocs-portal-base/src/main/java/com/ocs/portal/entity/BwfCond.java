package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "BWF_COND")
public class BwfCond {

    @EmbeddedId
    private BwfCondId id;

    @Column(name = "SORT_OPERATOR")
    private String sortOperator;

    @Column(name = "FUNCTION", length = 30)
    private String function;

    @Column(name = "RE_ATTR", nullable = false)
    private Integer reAttr;

    @Column(name = "PARAM1")
    private String param1;

    @Column(name = "PARAM2")
    private String param2;

    @Column(name = "IS_CONST", nullable = false)
    private String isConst;

    @Column(name = "R_RE_ATTR")
    private Integer rReAttr;

    @Column(name = "R_FUNCTION", length = 30)
    private String rFunction;

    @Column(name = "R_PARAM1")
    private String rParam1;

    @Column(name = "R_PARAM2")
    private String rParam2;

    @Column(name = "OPERAND", length = 30)
    private String operand;

    @Column(name = "ZONE_ID")
    private Integer zoneId;

    @Column(name = "FUNCTION_SCRIPT", length = 4000)
    private String functionScript;

    @Column(name = "R_FUNCTION_SCRIPT", length = 4000)
    private String rFunctionScript;

    @Column(name = "SP_ID")
    private Integer spId;



}