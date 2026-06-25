package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "BFM_SECURITY_RULE", schema = "POT")
public class BfmSecurityRule {

    @Id
    @Column(name = "LEVEL_ID")
    private Integer levelId;

    @Column(name = "LEVEL_CODE", length = 60)
    private String levelCode;

    @Column(name = "USER_CODE_COMPOSITION", length = 1)
    private String userCodeComposition;

    @Column(name = "USER_CODE_MIN_LENGTH")
    private Integer userCodeMinLength;

    @Column(name = "USER_CODE_MAX_LENGTH")
    private Integer userCodeMaxLength;

    @Column(name = "PWD_MIN_LENGTH")
    private Integer pwdMinLength;

    @Column(name = "PWD_COMPOSITION", length = 1)
    private String pwdComposition;

    @Column(name = "PWD_HIS_NUM")
    private Integer pwdHisNum;

    @Column(name = "PWD_EXC_DAYS")
    private Integer pwdExcDays;

    @Column(name = "MAX_LOGIN_RETRY")
    private Integer maxLoginRetry;

    @Column(name = "USER_ONLINE_NBR")
    private Integer userOnlineNbr;

    @Column(name = "STATE", length = 1)
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;

    @Column(name = "STAFF_ID")
    private Integer staffId;

    @Column(name = "PWD_MAX_LENGTH")
    private Integer pwdMaxLength;

    @Column(name = "USER_EXPIRE_DAYS")
    private Integer userExpireDays;

    @Column(name = "PWD_RULES", length = 60)
    private String pwdRules;

    @Column(name = "PWD_KEYBOARD_NUM")
    private Integer pwdKeyboardNum;

    @Column(name = "PWD_DICT_NUM")
    private Integer pwdDictNum;

    @Column(name = "USER_EXC_DAYS")
    private Integer userExcDays;

    @Column(name = "USER_CODE_FORMAT", length = 255)
    private String userCodeFormat;

}
