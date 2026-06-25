package com.ocs.portal.entity;

import java.time.LocalDateTime;

import com.ocs.portal.entity.embeddable.CustHisId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "CUST_HIS", schema = "STS")
@Data
public class CustHis {

  @EmbeddedId
  private CustHisId id;

  @Column(name = "CUST_CODE")
  private String custCode;

  @Column(name = "CUST_NAME")
  private String custName;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "SECOND_NAME")
  private String secondName;

  @Column(name = "THIRD_NAME")
  private String thirdName;

  @Column(name = "FOUR_NAME")
  private String fourName;

  @Column(name = "CUST_TYPE")
  private String custType;

  @Column(name = "CERT_ID")
  private Long certId;

  @Column(name = "PARENT_ID")
  private Long parentId;

  @Column(name = "AREA_ID")
  private Long areaId;

  @Column(name = "IMP_GRADE_ID")
  private Long impGradeId;

  @Column(name = "OCCUPATION_ID")
  private Long occupationId;

  @Column(name = "ZIPCODE")
  private String zipcode;

  @Column(name = "STD_ADDR_ID")
  private Long stdAddrId;

  @Column(name = "RELIGION_ID")
  private Long religionId;

  @Column(name = "CUST_TITLE_ID")
  private Long custTitleId;

  @Column(name = "INDUSTRY_ID")
  private Long industryId;

  @Column(name = "ADDRESS")
  private String address;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "GENDER")
  private String gender;

  @Column(name = "BIRTHDAY_DAY")
  private LocalDateTime birthdayDay;

  @Column(name = "PHONE_NUMBER")
  private String phoneNumber;

  @Column(name = "STATE")
  private String state;

  @Column(name = "STATE_DATE")
  private LocalDateTime stateDate;

  @Column(name = "PWD")
  private String pwd;

  @Column(name = "PARTY_TYPE")
  private String partyType;

  @Column(name = "PARTY_CODE")
  private String partyCode;

  @Column(name = "ROUTING_ID")
  private Long routingId;

  @Column(name = "NET_TYPE")
  private String netType;

  @Column(name = "COMMENTS")
  private String comments;

  @Column(name = "NEED_UPLOAD")
  private String needUpload;

  @Column(name = "CUST_CREDIT_GRADE_ID")
  private Long custCreditGradeId;

  @Column(name = "SP_ID")
  private Long spId;

  @Lob
  @Column(name = "EX_CUST_ATTR")
  private String exCustAttr;

  @Column(name = "EFF_DATE")
  private LocalDateTime effDate;

  @Column(name = "EXP_DATE")
  private LocalDateTime expDate;

  @Column(name = "REC_CREATED_DATE")
  private LocalDateTime recCreatedDate;

  @Column(name = "CREATE_PARTY_CODE")
  private String createPartyCode;

  @Column(name = "CREATE_PARTY_TYPE")
  private String createPartyType;

  @Column(name = "PART_ID")
  private Long partId;

  @Lob
  @Column(name = "EXT_ATTR")
  private String extAttr;

  @Column(name = "CONTACT_FIX_NBR")
  private String contactFixNbr;

  @Column(name = "VAT_NO")
  private String vatNo;

  @Column(name = "DEF_LANG_ID")
  private Long defLangId;

  @Column(name = "CUST_SEGMENT")
  private String custSegment;

  @Column(name = "CUST_SUB_SEGMENT")
  private String custSubSegment;
}
