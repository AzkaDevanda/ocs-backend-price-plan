package com.sts.sinorita.svc.role.projection;

public interface StafDtoProjection {

    Long getStaffId();

    String getStaffName();

    String getStaffCode();

    String getEmail();

    String getPhone();

    String getAddress();

    java.time.LocalDateTime getCreatedDate();

    String getState();

    java.time.LocalDateTime getStateDate();

    Long getUserId();

    Long getOrgId();

    String getOrgType();

    Long getStaffOrgId();

    Long getStaffJobId();

    java.time.LocalDateTime getUpdateDate();

    boolean isRecursive();

    String getRemark();

    String getExtStr01();

    String getExtStr02();

    String getExtStr03();

    String getExtStr08();

    String getExtStr09();

    String getExtStr10();

    String getExtStr11();

    String getExtStr04();

    String getExtStr05();

    String getExtStr06();

    String getExtStr07();

    String getExtStr12();

    Long getExtNum02();

    Long getExtNum01();

    java.time.LocalDateTime getExtDat01();

    java.time.LocalDateTime getExtDat02();

    String getUserName();

    String getUserCode();

    Long getSpId();

    String getOrderFields();

    String getFilterStaff();

    String getCreator();
}

