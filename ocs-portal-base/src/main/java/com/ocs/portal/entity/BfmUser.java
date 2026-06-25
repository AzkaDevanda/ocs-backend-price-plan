package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BFM_USER", schema = "POT")
public class BfmUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "T_BFM_USER_ID_SEQ", allocationSize = 1)
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "USER_CODE", nullable = false)
    private String userCode;

    @JsonIgnore
    @Column(name = "PWD", nullable = false)
    private String pwd;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "MEMO")
    private String memo;

    @Column(name = "USER_EFF_DATE", nullable = false)
    private LocalDateTime userEffDate;

    @Column(name = "USER_EXP_DATE")
    private LocalDateTime userExpDate;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "STATE", nullable = false)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "IS_LOCKED", nullable = false)
    private String isLocked;

    @Column(name = "PWD_EXP_DATE")
    private LocalDateTime pwdExpDate;

    @Column(name = "LOGIN_FAIL", nullable = false)
    private Integer loginFail;

    @Column(name = "UNLOCK_DATE")
    private LocalDateTime unlockDate;

    @Column(name = "PORTAL_ID")
    private Integer portalId;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "SRC_ID")
    private Integer srcId;

    @Column(name = "CONTACT_INFO")
    private String contactInfo;

    @Column(name = "FORCE_LOGIN")
    private String forceLogin;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "LOGIN_SUCCESS")
    private Integer loginSuccess;

    @Column(name = "OPEN_ID")
    private String openId;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "ALIAS")
    private String alias;

    @Column(name = "LAST_LOGIN_DATE")
    private LocalDateTime lastLoginDate;

    @Column(name = "SECURITY_QUESTION_ID")
    private Integer securityQuestionId;

    @Column(name = "SECURITY_ANSWER")
    private String securityAnswer;

    @Column(name = "THUMBNAIL_URI")
    private String thumbnailUri;

    @Column(name = "EXT_ATTR")
    private String extAttr;

    @Column(name = "CREATED_ID")
    private Integer createdId;

    @Column(name = "HEAD_IMG")
    private String headImg;

    @Column(name = "LOGIN_FAIL_DATE")
    private LocalDateTime loginFailDate;

    @Column(name = "LOGIN_IP")
    private String loginIp;

    @Column(name = "ROLE_ID")
    private Integer roleId;

    @Column(name = "UNIT")
    private String unit;
}
