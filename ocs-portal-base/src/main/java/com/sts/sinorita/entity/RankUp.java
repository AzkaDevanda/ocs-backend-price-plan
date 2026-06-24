package com.sts.sinorita.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "RANK_UP")
@SequenceGenerator(name = "RANK_UP_ID_SEQ", sequenceName = "RANK_UP_ID_SEQ", allocationSize = 1)
public class RankUp implements Serializable {
    @Id
    @Column(name = "RANK_UP_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RANK_UP_ID_SEQ")
    private Integer id;

    @Column(name = "UP_ID", nullable = false)
    private Integer upId;

    @Column(name = "OFFSET", nullable = false)
    private Integer offset;

    @Column(name = "DURATION", nullable = false)
    private Integer duration;

    @Column(name = "ADJUST_METHOD", nullable = false)
    private Character adjustMethod;

    @Column(name = "RATE")
    private Integer rate;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "RUM")
    private Long rum;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "TIME_SPAN_UP_ID")
    private Integer timeSpanUpId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUpId() {
        return upId;
    }

    public void setUpId(Integer upId) {
        this.upId = upId;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Character getAdjustMethod() {
        return adjustMethod;
    }

    public void setAdjustMethod(Character adjustMethod) {
        this.adjustMethod = adjustMethod;
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

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Integer getTimeSpanUpId() {
        return timeSpanUpId;
    }

    public void setTimeSpanUpId(Integer timeSpanUpId) {
        this.timeSpanUpId = timeSpanUpId;
    }

}