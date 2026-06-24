package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BFM_ORG", schema = "POT")
public class BfmOrg {
    @Id
    @Column(name = "ORG_ID")
    private Integer orgId;

    @Column(name = "PARENT_ORG_ID")
    private Integer parentOrgId;

    @Column(name = "ORG_NAME")
    private String orgName;

    @Column(name = "ORG_TYPE")
    private String orgType;

    @Column(name = "AREA_ID")
    private Integer areaId;

    @Column(name = "STATE")
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;

    @Column(name = "ORG_CODE")
    private String orgCode;

    @Column(name = "EXT_STR_01")
    private String extStr01;

    @Column(name = "EXT_STR_02")
    private String extStr02;

    @Column(name = "EXT_STR_03")
    private String extStr03;

    @Column(name = "EXT_STR_04")
    private String extStr04;

    @Column(name = "EXT_STR_05")
    private String extStr05;

    @Column(name = "EXT_STR_06")
    private String extStr06;

    @Column(name = "EXT_STR_07")
    private String extStr07;

    @Column(name = "EXT_STR_08")
    private String extStr08;

    @Column(name = "EXT_STR_09")
    private String extStr09;

    @Column(name = "EXT_STR_10")
    private String extStr10;

    @Column(name = "EXT_STR_11")
    private String extStr11;

    @Column(name = "EXT_STR_12")
    private String extStr12;

    @Column(name = "EXT_STR_13")
    private String extStr13;

    @Column(name = "EXT_NUM_01")
    private Long extNum01;

    @Column(name = "EXT_NUM_02")
    private Long extNum02;

    @Column(name = "EXT_DAT_01")
    private LocalDate extDat01;

    @Column(name = "EXT_DAT_02")
    private LocalDate extDat02;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "LEADER")
    private Long leader;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;
}
