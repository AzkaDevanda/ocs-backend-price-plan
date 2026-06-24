package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CUST", schema = "STS")
public class CustEntity {

    @Id
    @Column(name = "CUST_ID", nullable = false)
    private Long custId;

    @Column(name = "CUST_CODE", nullable = false, length = 60)
    private String custCode;

    @Column(name = "CUST_NAME", nullable = false, length = 255)
    private String custName;

    @Column(name = "FIRST_NAME", length = 255)
    private String firstName;

    @Column(name = "SECOND_NAME", length = 255)
    private String secondName;

    @Column(name = "THIRD_NAME", length = 255)
    private String thirdName;

    @Column(name = "FOUR_NAME", length = 255)
    private String fourName;

    @Column(name = "CUST_TYPE", nullable = false, length = 1)
    private String custType;

    @Column(name = "CERT_ID")
    private Long certId;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "AREA_ID")
    private Long areaId;

    @Column(name = "IMP_GRADE_ID")
    private Integer impGradeId;

    @Column(name = "ADDRESS", length = 4000)
    private String address;

    @Column(name = "STD_ADDR_ID")
    private Long stdAddrId;

    @Column(name = "NEED_UPLOAD", length = 1)
    private String needUpload;

    @Column(name = "ZIPCODE", length = 60)
    private String zipcode;

    @Column(name = "RELIGION_ID")
    private Long religionId;

    @Column(name = "INDUSTRY_ID")
    private Long industryId;

    @Column(name = "OCCUPATION_ID")
    private Long occupationId;

    @Column(name = "CUST_TITLE_ID")
    private Long custTitleId;

    @Column(name = "CUST_CREDIT_GRADE_ID")
    private Long custCreditGradeId;

    @Column(name = "EMAIL", length = 255)
    private String email;

    @Column(name = "GENDER", length = 1)
    private String gender;

    @Column(name = "BIRTHDAY_DAY")
//    @Temporal(TemporalType.DATE)
    private LocalDateTime birthdayDay;

    @Column(name = "PHONE_NUMBER", length = 60)
    private String phoneNumber;

    @Column(name = "CREATED_DATE", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Column(name = "UPDATE_DATE", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updateDate;

    @Column(name = "NET_TYPE", length = 1)
    private String netType;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime stateDate;

    @Column(name = "PWD", length = 120)
    private String pwd;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "PARTY_TYPE", length = 1)
    private String partyType;

    @Column(name = "ROUTING_ID")
    private Long routingId;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "CREATE_PARTY_CODE", length = 30)
    private String createPartyCode;

    @Column(name = "CREATE_PARTY_TYPE", length = 1)
    private String createPartyType;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "CONTACT_FIX_NBR", length = 60)
    private String contactFixNbr;

    @Column(name = "VAT_NO", length = 120)
    private String vatNo;

    @Column(name = "DEF_LANG_ID")
    private Long defLangId;

    @Column(name = "ENGLISH_NAME", length = 255)
    private String englishName;

    @Column(name = "CUST_SEGMENT", length = 1)
    private String custSegment;

    @Column(name = "CUST_SUB_SEGMENT", length = 1)
    private String custSubSegment;

    // Getters and Setters (boleh generate otomatis pakai Lombok @Getter @Setter jika diperlukan)

}
