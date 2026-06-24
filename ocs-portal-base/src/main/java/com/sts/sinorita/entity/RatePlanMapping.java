package com.sts.sinorita.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "RATE_PLAN_MAPPING")
public class RatePlanMapping implements Serializable {

    @EmbeddedId
    private RatePlanMappingId id;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "SP_ID")
    private Integer spId;

    public RatePlanMappingId getId() {
        return id;
    }

    public void setId(RatePlanMappingId id) {
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