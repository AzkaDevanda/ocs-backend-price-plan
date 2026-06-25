package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.SortAttrMatchId;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "SORT_ATTR_MATCH")
public class SortAttrMatch implements Serializable {

    @EmbeddedId
    private SortAttrMatchId id;

    @Column(name = "RE_ATTR")
    private Integer reAttr;

    @Column(name = "FUNCTION", length = 30)
    private String functionName;

    @Column(name = "PARAM1", length = 255)
    private String param1;

    @Column(name = "PARAM2", length = 255)
    private String param2;

    @Column(name = "IS_CONST", nullable = false, length = 1)
    private String isConst;

    @Column(name = "R_RE_ATTR")
    private Integer rReAttr;

    @Column(name = "R_FUNCTION", length = 30)
    private String rFunction;

    @Column(name = "R_PARAM1", length = 255)
    private String rParam1;

    @Column(name = "R_PARAM2", length = 255)
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

    @Column(name = "SORT_OPERATOR", nullable = false, length = 1)
    private String sortOperator;
}
