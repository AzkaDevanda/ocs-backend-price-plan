package com.ocs.portal.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "ACM_UP")
@SequenceGenerator(name = "ACM_UP_ID_SEQ", sequenceName = "ACM_UP_ID_SEQ", allocationSize = 1)
public class AcmUp implements Serializable {
    @Id
    @Column(name = "ACM_UP_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACM_UP_ID_SEQ")
    private Integer id;

    @Column(name = "RESOURCE_ID")
    private Integer resourceId;

    @Column(name = "ADJUST_METHOD")
    private Character adjustMethod;

    @Column(name = "UP_ID", nullable = false)
    private Integer upId;

    @Column(name = "TIME_SPAN_UP_ID")
    private Integer timeSpanUpId;

    @Column(name = "RATE")
    private Integer rate;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "RUM")
    private Long rum;

    @Column(name = "EFF_VALUE")
    private Long effValue;

    @Column(name = "EXP_VALUE")
    private Long expValue;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "REF_VALUE_ID")
    private Integer refValueId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Character getAdjustMethod() {
        return adjustMethod;
    }

    public void setAdjustMethod(Character adjustMethod) {
        this.adjustMethod = adjustMethod;
    }

    public Integer getUpId() {
        return upId;
    }

    public void setUpId(Integer upId) {
        this.upId = upId;
    }

    public Integer getTimeSpanUpId() {
        return timeSpanUpId;
    }

    public void setTimeSpanUpId(Integer timeSpanUpId) {
        this.timeSpanUpId = timeSpanUpId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getRum() {
        return rum;
    }

    public void setRum(Long rum) {
        this.rum = rum;
    }

    public Long getEffValue() {
        return effValue;
    }

    public void setEffValue(Long effValue) {
        this.effValue = effValue;
    }

    public Long getExpValue() {
        return expValue;
    }

    public void setExpValue(Long expValue) {
        this.expValue = expValue;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Integer getRefValueId() {
        return refValueId;
    }

    public void setRefValueId(Integer refValueId) {
        this.refValueId = refValueId;
    }

}