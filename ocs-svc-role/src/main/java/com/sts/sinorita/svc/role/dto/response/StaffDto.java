package com.sts.sinorita.svc.role.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StaffDto {
    private static final long serialVersionUID = 4539394976734347960L;

    private Long staffId;

    private String staffName;

    private String staffCode;

    private String email;

    private String phone;

    private String address;

    private LocalDate createdDate;

    private String state;

    private LocalDate stateDate;

    private Long userId;

    private Long orgId;

    private String orgType;

    private Long staffOrgId;

    private Long staffJobId;

    private LocalDateTime updateDate;

    private boolean recursive = false;

    private String remark;

    private String extStr01;

    private String extStr02;

    private String extStr03;

    private String extStr08;

    private String extStr09;

    private String extStr10;

    private String extStr11;

    private String extStr04;

    private String extStr05;

    private String extStr06;

    private String extStr07;

    private String extStr12;

    private Long extNum02;

    private Long extNum01;

    private LocalDateTime extDat01;

    private LocalDateTime extDat02;

    private String userName;

    private String userCode;

    private Long spId;

    private String orderFields;

    private String filterStaff;

    private String creator;

}
