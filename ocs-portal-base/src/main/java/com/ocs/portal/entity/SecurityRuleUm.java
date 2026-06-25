package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "BFM_SECURITY_RULE_UM", schema = "POT")
public class SecurityRuleUm implements Serializable {

    @Id
    @Column(name = "RULE_ID")
    private Long ruleId;

    @Column(name = "ACCOUNT_TYPE", length = 60)
    private String accountType;

    @Column(name = "PWD_MIN_LENGTH")
    private Integer pwdMinLength;

    @Column(name = "PWD_COMPLEXITY", length = 8)
    private String pwdComplexity;

    @Column(name = "PWD_COMPOSITION", length = 1)
    private String pwdComposition;

    @Column(name = "PWD_HIS_NUM")
    private Integer pwdHisNum;

    @Column(name = "PWD_EXC_DAYS")
    private Integer pwdExcDays;

    @Column(name = "MAX_LOGIN_RETRY")
    private Integer maxLoginRetry;

    @Column(name = "USER_SESSION_TIMEOUT")
    private Integer userSessionTimeout;

    @Column(name = "AUTO_INACTIVE_DAYS")
    private Integer autoInactiveDays;
}
