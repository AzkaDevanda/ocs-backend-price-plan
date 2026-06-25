package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "RP_MAPPING_PRICE")
public class RpMappingPrice implements Serializable {
    @Id
    @Column(name = "RP_MAPPING_PRICE_ID", nullable = false)
    private Integer id;

    @Column(name = "RP_ID", nullable = false)
    private Integer rpId;

    @Column(name = "RP_MAPPING_BRANCH_ID")
    private Integer rpMappingBranchId;

    @Column(name = "PRIORITY", nullable = false)
    private Integer priority;

    @Column(name = "PRICE_TYPE", nullable = false)
    private Boolean priceType = false;

    @Column(name = "PRICE_UNIT", nullable = false)
    private Boolean priceUnit = false;

    @Column(name = "VALUE", nullable = false)
    private Long value;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "DAY_CHARGED_RULE_ID")
    private Integer dayChargedRuleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRpId() {
        return rpId;
    }

    public void setRpId(Integer rpId) {
        this.rpId = rpId;
    }

    public Integer getRpMappingBranchId() {
        return rpMappingBranchId;
    }

    public void setRpMappingBranchId(Integer rpMappingBranchId) {
        this.rpMappingBranchId = rpMappingBranchId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getPriceType() {
        return priceType;
    }

    public void setPriceType(Boolean priceType) {
        this.priceType = priceType;
    }

    public Boolean getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Boolean priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Integer getDayChargedRuleId() {
        return dayChargedRuleId;
    }

    public void setDayChargedRuleId(Integer dayChargedRuleId) {
        this.dayChargedRuleId = dayChargedRuleId;
    }

}