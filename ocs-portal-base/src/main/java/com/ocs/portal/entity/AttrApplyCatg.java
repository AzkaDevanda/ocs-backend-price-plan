package com.ocs.portal.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "ATTR_APPLY_CATG")
public class AttrApplyCatg implements Serializable {
    @EmbeddedId
    private AttrApplyCatgId id;

    @Column(name = "SP_ID")
    private Integer spId;

    public AttrApplyCatgId getId() {
        return id;
    }

    public void setId(AttrApplyCatgId id) {
        this.id = id;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}