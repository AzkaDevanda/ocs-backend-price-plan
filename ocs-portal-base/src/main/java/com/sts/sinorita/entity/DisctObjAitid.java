package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "DISCT_OBJ_AITID")
public class DisctObjAitid implements Serializable {
    @EmbeddedId
    private DisctObjAitidId id;

    @Column(name = "PRIORITY", nullable = false)
    private Integer priority;

    @Column(name = "SP_ID")
    private Integer spId;

    public DisctObjAitidId getId() {
        return id;
    }

    public void setId(DisctObjAitidId id) {
        this.id = id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}