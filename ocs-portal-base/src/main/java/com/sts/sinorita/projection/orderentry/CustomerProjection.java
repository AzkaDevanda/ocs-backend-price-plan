package com.sts.sinorita.projection.orderentry;

import java.time.LocalDate;

public interface CustomerProjection {
    Long getCustId();
    String getCustCode();
    String getCustName();
    String getFirstName();
    String getSecondName();
    String getThirdName();
    String getFourName();
    String getCustType();
    String getEmail();
    String getCustTypeName();
    Long getCertId();
    Long getCustTitleId();
    String getCertNbr();
    Long getCertTypeId();
    String getCertIssueOrg();
    LocalDate getCertIssueDate();
    LocalDate getCertEffDate();
    LocalDate getCertExpDate();
    String getCertAddress();
    String getZipcode();
    String getAddress();
    java.util.Date getCreatedDate();
    java.util.Date getUpdateDate();
    String getState();
    java.util.Date getStateDate();
    Long getAreaId();
    Long getStdAddrId();
    Long getCustCreditGradeId();
    Long getReligionId();
    Long getIndustryId();
    Long getOccupationId();
    String getGender();
    String getPhoneNumber();
    String getComments();
    String getCertTypeName();
    Long getImpGradeId();
    String getImpGradeName();
    Integer getBirthdayDay();
    String getRoutingId();
}
