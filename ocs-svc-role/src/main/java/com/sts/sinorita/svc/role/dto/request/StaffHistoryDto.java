package com.sts.sinorita.svc.role.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StaffHistoryDto {

    private Long recId;

    private Long staffId;

    private String state;

    private LocalDate stateDate;

    private LocalDate createdDate;

    private Long userId;

    private Long recUserId;

    private String userCode;

    private String userName;

    private LocalDate recCreateDate;

    private String ipAddress;

    private String extStr09;

    private String extStr10;

    private String extStr11;

    private String email;

    private String phone;

    private String address;

    private String extStr12;

    private String extStr06;

    private String extStr07;

    private String extStr08;

    private String comments;

    private String staffCode;

    private String recUserName;

    private String staffName;

    private String remark;

    private String operatorType;

    private LocalDateTime extDat01;

    private LocalDateTime extDat02;

    private String extStr01;

    private String extStr02;

    private String extStr05;

    private Long extNum01;

    private Long extNum02;

    private String extStr03;

    private String extStr04;

    private String recUserCode;

    private Long spId;
}
