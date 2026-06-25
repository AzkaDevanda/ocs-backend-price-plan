package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "RE_RECURRING")
public class ReRecurring implements Serializable {
    @EmbeddedId
    private ReRecurringId id;

    @Column(name = "RECURRING_RE_TYPE", nullable = false)
    private Character recurringReType;

    @Column(name = "SP_ID")
    private Integer spId;

    public ReRecurringId getId() {
        return id;
    }

    public void setId(ReRecurringId id) {
        this.id = id;
    }

    public Character getRecurringReType() {
        return recurringReType;
    }

    public void setRecurringReType(Character recurringReType) {
        this.recurringReType = recurringReType;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}