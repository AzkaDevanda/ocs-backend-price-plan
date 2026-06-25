package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "BFM_USER_HIS", schema = "POT")
public class BfmUserHis {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userHisSeq")
    @SequenceGenerator(name = "userHisSeq", sequenceName = "T_BFM_USER_HIS_ID_SEQ", allocationSize = 1)
    @Column(name = "REC_ID")
    private Long recId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "USER_CODE")
    private String userCode;

    @Column(name = "PWD")
    private String pwd;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "MEMO")
    private String memo;

    @Column(name = "USER_EFF_DATE")
    private LocalDate userEffDate;

    @Column(name = "USER_EXP_DATE")
    private LocalDate userExpDate;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "STATE")
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;

    @Column(name = "IS_LOCKED")
    private String isLocked;

    @Column(name = "PWD_EXP_DATE")
    private LocalDate pwdExpDate;

    @Column(name = "LOGIN_FAIL")
    private Integer loginFail;

    @Column(name = "UNLOCK_DATE")
    private LocalDate unlockDate;

    @Column(name = "PORTAL_ID")
    private Long portalId;

    @Column(name = "REC_USER_ID")
    private Long recUserId;

    @Column(name = "REC_CREATE_DATE")
    private LocalDate recCreateDate;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "CONTACT_INFO")
    private String contactInfo;

    @Column(name = "FORCE_LOGIN")
    private String forceLogin;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

    @Column(name = "SRC_ID")
    private Long srcId;

    @Column(name = "LOGIN_SUCCESS")
    private Long loginSuccess;

    @Column(name = "OPEN_ID")
    private String openId;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "ALIAS")
    private String alias;

    @Column(name = "LAST_LOGIN_DATE")
    private LocalDate lastLoginDate;

    @Column(name = "SECURITY_QUESTION_ID")
    private Integer securityQuestionId;

    @Column(name = "SECURITY_ANSWER")
    private String securityAnswer;

    @Column(name = "THUMBNAIL_URI")
    private String thumbnailUri;

    @Column(name = "EXT_ATTR")
    private String extAttr;

    @Column(name = "OPERATOR_TYPE")
    private String operatorType;
}
