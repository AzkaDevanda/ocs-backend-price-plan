package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BFM_STAFF", schema = "POT")
public class BfmStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "staf_seq_gen")
    @SequenceGenerator(name = "staf_seq_gen", sequenceName = "T_BFM_STAFF_ID_SEQ", allocationSize = 1)
    @Column(name = "STAFF_ID", nullable = false)
    private Integer staffId;

    @Column(name = "STAFF_NAME", nullable = false)
    private String staffName;

    @Column(name = "STAFF_CODE", nullable = false)
    private String staffCode;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDate createdDate;

    @Column(name = "STATE", nullable = false)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "USER_ID")
    private Integer userId;

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

    @Column(name = "EXT_NUM_01")
    private Integer extNum01;

    @Column(name = "EXT_NUM_02")
    private Integer extNum02;

    @Column(name = "EXT_DAT_01")
    private LocalDate extDat01;

    @Column(name = "EXT_DAT_02")
    private LocalDate extDat02;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "REMARK")
    private String remark;
}
