package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "REF_VALUE")
@SequenceGenerator(name = "REF_VALUE_ID_SEQ", sequenceName = "STS.REF_VALUE_ID_SEQ", allocationSize = 1)
public class RefValue implements Serializable {
    @Id
    @Column(name = "REF_VALUE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REF_VALUE_ID_SEQ")
    private Integer id;

    @Column(name = "REF_VALUE_TYPE", nullable = false)
    private Character refValueType;

    @Column(name = "VALUE_STRING", length = 120)
    private String valueString;

    @Column(name = "SIMPLE_PARAM_ID")
    private Integer simpleParamId;

    @Column(name = "TABLE_PARAM_ID")
    private Integer tableParamId;

    @Column(name = "PAR_PARAM_VALUE_TYPE")
    private Short parParamValueType;

    @Column(name = "COL_ID")
    private Integer colId;

    @Column(name = "RE_ATTR")
    private Integer reAttr;

    @Column(name = "PARAM_VALUE_TYPE")
    private Boolean paramValueType;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "STAFF_ID")
    private Integer staffId;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDate createdDate;

    @Column(name = "STATE")
    private Character state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "COST_VALUE_TYPE")
    private Integer costValueType;

    @Column(name = "RATE_PRECISION")
    private Long ratePrecision;

    @Column(name = "PRICE_ID")
    private Long priceId;

    @Column(name = "RATE_PLAN_ID")
    private Integer ratePlanId;

    @Column(name = "OFFER_VER_ID")
    private Integer offerVerId;

    @Column(name = "PROMO_DISC_ACCT_ITEM_TYPE_ID")
    private Integer promoDiscAcctItemTypeId;

}