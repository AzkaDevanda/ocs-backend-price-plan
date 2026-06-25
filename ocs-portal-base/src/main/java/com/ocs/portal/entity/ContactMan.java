package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "CONTACT_MAN", schema = "STS")
@Getter
@Setter
public class ContactMan {

  @Id
  @Column(name = "CONTACT_MAN_ID")
  private Long contactManId;

  @Column(name = "CONTACT_MAN_TYPE")
  private Integer contactManType;

  @Column(name = "ACCT_ID")
  private Long acctId;

  @Column(name = "CUST_ID")
  private Long custId;

  @Column(name = "CUST_TITLE_ID")
  private Long custTitleId;

  @Column(name = "CONTACT_MAN_NAME")
  private String contactManName;

  @Column(name = "SEX")
  private String sex;

  @Column(name = "OFFICE_PHONE")
  private String officePhone;

  @Column(name = "HOME_PHONE")
  private String homePhone;

  @Column(name = "MOBILE_PHONE")
  private String mobilePhone;

  @Column(name = "FAX")
  private String fax;

  @Column(name = "ZIPCODE")
  private String zipcode;

  @Column(name = "MAIL_ADDR")
  private String mailAddr;

  @Column(name = "EMAIL_ADDR")
  private String emailAddr;

  @Column(name = "MAIN_COMM")
  private String mainComm;

  @Column(name = "COMMENTS")
  private String comments;

  @Column(name = "CREATED_DATE")
  private LocalDateTime createdDate;

  @Column(name = "UPDATE_DATE")
  private LocalDateTime updateDate;

  @Column(name = "PARTY_TYPE")
  private String partyType;

  @Column(name = "PARTY_CODE")
  private String partyCode;

  @Column(name = "STATE")
  private String state;

  @Column(name = "STATE_DATE")
  private LocalDateTime stateDate;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "CREATE_PARTY_CODE")
  private String createPartyCode;

  @Column(name = "CREATE_PARTY_TYPE")
  private String createPartyType;

  @Column(name = "PRIMARY_FLAG")
  private String primaryFlag;

  @Column(name = "SUBS_ID")
  private Long subsId;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "SECOND_NAME")
  private String secondName;

  @Column(name = "THIRD_NAME")
  private String thirdName;

  @Column(name = "FOUR_NAME")
  private String fourName;

  @Column(name = "STD_ADDR_ID")
  private Long stdAddrId;

  @Column(name = "ADDRESS")
  private String address;

  @Column(name = "DEF_LANG_ID")
  private Long defLangId;

  @Column(name = "ROUTING_ID")
  private Long routingId;

  @Column(name = "CONTACT_MAN_CODE")
  private String contactManCode;

  @Column(name = "CERT_ID")
  private Long certId;

  @Column(name = "FACEBOOK")
  private String facebook;

  @Column(name = "TWITTER")
  private String twitter;

  @Column(name = "INSTAGRAM")
  private String instagram;
}
