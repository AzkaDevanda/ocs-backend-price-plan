package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UP")
public class Up implements Serializable {
    @Id
    @Column(name = "UP_ID", nullable = false)
    private Integer id;

    @Column(name = "REPEAT_POS")
    private Integer repeatPos;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "ACMCALC_REPORT_POS")
    private Integer acmcalcReportPos;

    @Column(name = "ACMCALC_REPORT_NUM")
    private Integer acmcalcReportNum;

    @Column(name = "ACMCALC_REPORT_END")
    private Integer acmcalcReportEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepeatPos() {
        return repeatPos;
    }

    public void setRepeatPos(Integer repeatPos) {
        this.repeatPos = repeatPos;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Integer getAcmcalcReportPos() {
        return acmcalcReportPos;
    }

    public void setAcmcalcReportPos(Integer acmcalcReportPos) {
        this.acmcalcReportPos = acmcalcReportPos;
    }

    public Integer getAcmcalcReportNum() {
        return acmcalcReportNum;
    }

    public void setAcmcalcReportNum(Integer acmcalcReportNum) {
        this.acmcalcReportNum = acmcalcReportNum;
    }

    public Integer getAcmcalcReportEnd() {
        return acmcalcReportEnd;
    }

    public void setAcmcalcReportEnd(Integer acmcalcReportEnd) {
        this.acmcalcReportEnd = acmcalcReportEnd;
    }
}