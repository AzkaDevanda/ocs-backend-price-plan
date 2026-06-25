package com.ocs.portal.svc.role.dto.response;

import lombok.Data;

import java.io.Serializable;


@Data
public class SecurityRuleDto implements Serializable {
    private static final long serialVersionUID = -9072692687469713338L;

    private Long levelId;

    private String levelCode;

    private String userCodeComposition;

    private Long userCodeMinLength;

    private Long userCodeMaxLength;

    private Long pwdMinLength;

    private Long pwdMaxLength;

    private String pwdComposition;

    private Long pwdHisNum;

    private Long pwdExcDays;

    private String pwdRules;

    private int pwdKeyboardNum;

    private int pwdDictNum;

    private int userExcDays;

    private String sessionMaxExceeded;

}
