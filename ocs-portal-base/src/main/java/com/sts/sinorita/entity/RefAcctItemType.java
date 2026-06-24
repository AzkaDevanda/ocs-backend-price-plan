package com.sts.sinorita.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "REF_ACCT_ITEM_TYPE")
public class RefAcctItemType implements Serializable {
    @EmbeddedId
    private RefAcctItemTypeId id;

    @Column(name = "SP_ID")
    private Integer spId;

    public RefAcctItemTypeId getId() {
        return id;
    }

    public void setId(RefAcctItemTypeId id) {
        this.id = id;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}