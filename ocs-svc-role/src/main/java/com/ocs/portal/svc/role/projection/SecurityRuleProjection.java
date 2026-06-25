package com.ocs.portal.svc.role.projection;

public interface SecurityRuleProjection {
    Long getLevelId();
    String getLevelCode();
    String getUserCodeComposition();
    Integer getUserCodeMinLength();
    Integer getUserCodeMaxLength();
    Integer getPwdMinLength();
    String getPwdComposition();
    Integer getPwdHisNum();
    Integer getPwdExcDays();
    String getPwdRules();
    Integer getPwdKeyboardNum();
    Integer getPwdDictNum();
    Integer getUserExcDays();
}
