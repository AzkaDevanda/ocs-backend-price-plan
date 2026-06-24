package com.sts.sinorita.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "PRICE_VER")
@SequenceGenerator(name = "price_ver_seq", sequenceName = "STS.PRICE_VER_ID_SEQ", allocationSize = 1)
public class PriceVer implements Serializable {
    @Id
    @Column(name = "PRICE_VER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_ver_seq")
    private Integer id;

    @Column(name = "RATE_PLAN_ID")
    private Integer ratePlanId;

    @Column(name = "MAPPING_ID")
    private Integer mappingId;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @Column(name = "SP_ID")
    private Integer spId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRatePlanId() {
        return ratePlanId;
    }

    public void setRatePlanId(Integer ratePlanId) {
        this.ratePlanId = ratePlanId;
    }

    public Integer getMappingId() {
        return mappingId;
    }

    public void setMappingId(Integer mappingId) {
        this.mappingId = mappingId;
    }

    public LocalDate getEffDate() {
        return effDate;
    }

    public void setEffDate(LocalDate effDate) {
        this.effDate = effDate;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}