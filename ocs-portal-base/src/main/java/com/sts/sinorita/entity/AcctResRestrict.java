package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.AcctResRestrictId;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "ACCT_RES_RESTRICT")
public class AcctResRestrict implements Serializable {
    @EmbeddedId
    private AcctResRestrictId id;

    @Column(name = "RE_ATTR", nullable = false)
    private Integer reAttr;

    @Column(name = "FUNCTION", length = 30)
    private String functionName;

    @Column(name = "PARAM1", length = 30)
    private String param1;

    @Column(name = "PARAM2", length = 30)
    private String param2;

    @Column(name = "SORT_OPERATOR", nullable = false, length = 1)
    private String sortOperator;

    @Column(name = "IS_CONST", nullable = false, length = 1)
    private String isConst;

    @Column(name = "R_RE_ATTR")
    private Integer rReAttr;

    @Column(name = "R_FUNCTION", length = 30)
    private String rFunction;

    @Column(name = "R_PARAM1", length = 30)
    private String rParam1;

    @Column(name = "R_PARAM2", length = 30)
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
