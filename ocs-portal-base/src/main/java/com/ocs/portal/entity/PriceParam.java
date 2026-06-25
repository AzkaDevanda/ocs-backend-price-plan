package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "PRICE_PARAM")
public class PriceParam implements Serializable {
    @Id
    @Column(name = "PRICE_PARAM_ID", nullable = false)
    private Long priceParamId;

    @Column(name = "PRICE_ID", nullable = false)
    private Long priceId;

    @Column(name = "PARAM_TYPE", nullable = false, length = 1)
    private String paramType;

    @Column(name = "SIMPLE_PARAM_ID")
    private Long simpleParamId;

    @Column(name = "COL_ID")
    private Long colId;

    @Column(name = "TABLE_PARAM_ID")
    private Long tableParamId;

    @Column(name = "SP_ID")
    private Long spId;
}
