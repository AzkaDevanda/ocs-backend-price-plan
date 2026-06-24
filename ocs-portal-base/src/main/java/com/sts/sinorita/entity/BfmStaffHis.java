package com.sts.sinorita.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "BFM_STAFF_HIS", schema = "POT")
public class BfmStaffHis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "staf_his_seq_gen")
    @SequenceGenerator(name = "staf_his_seq_gen", sequenceName = "T_BFM_STAFF_HIS_ID_SEQ", allocationSize = 1)
    @Column(name = "REC_ID")
    private Long recId;

    @Column(name = "STAFF_ID")
    private Long staffId;

    @Column(name = "STAFF_NAME", length = 60)
    private String staffName;

    @Column(name = "STAFF_CODE", length = 60)
    private String staffCode;

    @Column(name = "EMAIL", length = 255)
    private String email;

    @Column(name = "PHONE", length = 60)
    private String phone;

    @Column(name = "ADDRESS", length = 255)
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "STATE", length = 1)
    private String state;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STATE_DATE")
    private Date stateDate;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "EXT_STR_01", length = 120)
    private String extStr01;

    @Column(name = "EXT_STR_02", length = 120)
    private String extStr02;

    @Column(name = "EXT_STR_03", length = 120)
    private String extStr03;

    @Column(name = "EXT_STR_04", length = 120)
    private String extStr04;

    @Column(name = "EXT_STR_05", length = 120)
    private String extStr05;

    @Column(name = "EXT_STR_06", length = 120)
    private String extStr06;

    @Column(name = "EXT_STR_07", length = 120)
    private String extStr07;

    @Column(name = "EXT_STR_08", length = 120)
    private String extStr08;

    @Column(name = "EXT_STR_09", length = 120)
    private String extStr09;

    @Column(name = "EXT_STR_10", length = 120)
    private String extStr10;

    @Column(name = "EXT_STR_11", length = 120)
    private String extStr11;

    @Column(name = "EXT_STR_12", length = 120)
    private String extStr12;

    @Column(name = "EXT_NUM_01")
    private Long extNum01;

    @Column(name = "EXT_NUM_02")
    private Long extNum02;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXT_DAT_01")
    private Date extDat01;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXT_DAT_02")
    private Date extDat02;

    @Column(name = "REC_USER_ID")
    private Long recUserId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REC_CREATE_DATE")
    private Date recCreateDate;

    @Column(name = "IP_ADDRESS", length = 60)
    private String ipAddress;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "CREATOR", length = 60)
    private String creator;

    @Column(name = "OPERATOR_TYPE", length = 1)
    private String operatorType;

    @Column(name = "REMARK", length = 255)
    private String remark;
}