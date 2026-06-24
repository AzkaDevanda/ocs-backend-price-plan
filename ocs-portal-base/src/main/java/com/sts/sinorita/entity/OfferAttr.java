package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "OFFER_ATTR")
public class OfferAttr implements Serializable {
    @EmbeddedId
    private OfferAttrId id;

    @Column(name = "DEFAULT_VALUE", length = 4000)
    private String defaultValue;

    @Column(name = "DISP_ORDER")
    private Integer dispOrder;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "OPERATION_TYPES", length = 30)
    private String operationTypes;

    public OfferAttrId getId() {
        return id;
    }

    public void setId(OfferAttrId id) {
        this.id = id;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public String getOperationTypes() {
        return operationTypes;
    }

    public void setOperationTypes(String operationTypes) {
        this.operationTypes = operationTypes;
    }

}