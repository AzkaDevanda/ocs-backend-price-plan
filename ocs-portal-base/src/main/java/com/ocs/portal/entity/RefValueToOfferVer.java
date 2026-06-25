package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "REF_VALUE_TO_OFFER_VER")
public class RefValueToOfferVer implements Serializable {
    @EmbeddedId
    private RefValueToOfferVerId id;

    @Column(name = "SP_ID")
    private Integer spId;

    public RefValueToOfferVerId getId() {
        return id;
    }

    public void setId(RefValueToOfferVerId id) {
        this.id = id;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}