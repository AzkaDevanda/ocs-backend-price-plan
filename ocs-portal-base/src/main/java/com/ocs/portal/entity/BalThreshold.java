package com.ocs.portal.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "BAL_THRESHOLD")
public class BalThreshold implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BAL_THRESHOLD_ID_SEQ")
    @SequenceGenerator(name = "BAL_THRESHOLD_ID_SEQ", sequenceName = "BAL_THRESHOLD_ID_SEQ", allocationSize = 1)
    @Column(name = "BAL_THRESHOLD_ID", nullable = false)
    private Integer id;

    @Column(name = "TRIGGER_ID", nullable = false)
    private Integer triggerId;

    @Column(name = "RE_ATTR")
    private Integer reAttr;

    @Column(name = "VALUE", nullable = false)
    private Long value;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "INTERVAL")
    private Long interval;

    @Column(name = "RATIO")
    private Integer ratio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(Integer triggerId) {
        this.triggerId = triggerId;
    }

    public Integer getReAttr() {
        return reAttr;
    }

    public void setReAttr(Integer reAttr) {
        this.reAttr = reAttr;
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

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

}