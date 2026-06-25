package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class CustDto {
  public Long custId;

  public String custCode;

  public String custName;

  public String firstName;

  public String secondName;

  public String thirdName;

  public String fourName;

  public String custType;

  public Long certId;

  public Long parentId;

  public Long areaId;

  public Long impGradeId;

  public String address;

  public Long stdAddrId;

  public String needUpload;

  public String zipcode;

  public Long religionId;

  public Long industryId;

  public Long occupationId;

  public Long custTitleId;

  public String email;

  public String gender;

  public Date birthdayDay;

  public String phoneNumber;

  public Date createdDate;

  public Date updateDate;

  public String netType;

  public String state;

  public Date stateDate;

  public String pwd;

  public String partyCode;

  public String partyType;

  public Long routingId;

  public String comments;

  public Long spId;

  public Long custCreditGradeId;

  public CertDto certDto;

  public String createPartyType;

  public String createPartyCode;

  public Long defLangId;

  public String contactFixNbr;

  public String vatNo;
  public String custSegment;
  public String custSubSegment;
  private String isWs;
  private String operationType;
}
