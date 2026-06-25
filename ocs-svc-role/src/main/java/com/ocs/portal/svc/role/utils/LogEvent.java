package com.ocs.portal.svc.role.utils;


import java.io.Serializable;

public enum LogEvent implements Serializable {
    LOGIN_SUCCESS("login", "LOGIN_SUCCESS", "Login success."),
    LOGOUT_SUCCESS("logout", "LOGOUT_NORMAL", "Logout success."),
    ACCESS_DENIED("security audit", "URL_AUDIT_FAIL", "access denied."),
    SECURITY_CSRF("security audit", "CSRF_ATTACK", "You may be attacked by CSRF."),
    SECURITY_XSS("security audit", "XSS_ATTACK", "You may be attacked by XSS"),
    SECURITY_FILE_UPLOAD("security audit", "FILE_UPLOAD", "comments"),
    SECURITY_FILE_DOWNLOAD("security audit", "FILE_DOWNLOAD", "comments"),
    SECURITY_ARGUMENT_NOT_VALID("security audit", "ARGUMENT_NOT_VALID", "comments"),
    LOGOUT_TIMEOUT("logout", "LOGOUT_TIMEOUT", "Logout due to session time out."),
    LOGOUT_FORCE("logout", "LOGOUT_FORCE", "Logout success."),
    ADD_USER("usermgr", "ADD_USER", "Add user."),
    MODIFY_USER("usermgr", "MODIFY_USER", "Modify user."),
    LOCK_USER("usermgr", "LOCK_USER", "Lock user."),
    ACTIVE_USER("usermgr", "ACTIVE_USER", "Active user."),
    UNLOCK_USER("usermgr", "UNLOCK_USER", "Unlock user."),
    ENABLE_USER("usermgr", "ENABLE_USER", "Enable user."),
    DISABLE_USER("usermgr", "DISABLE_USER", "Disable user."),
    DELETE_USER("usermgr", "DELETE_USER", "Delete user."),
    MODIFY_PASSWORD("usermgr", "MODIFY_PASSWORD", "Modify user password."),
    RESET_PASSWORD("usermgr", "RESET_PASSWORD", "Reset user password."),
    FORGET_PASSWORD("usermgr", "FORGET_PASSWORD", "Forget user password."),
    GRANT_ROLE_TO_USER("usermgr", "GRANT_ROLE_TO_USER", "Grant role to user."),
    UNGRANT_ROLE_TO_USER("usermgr", "UNGRANT_ROLE_TO_USER", "Ungrant role to user."),
    GRANT_PORTAL_TO_USER("usermgr", "GRANT_PORTAL_TO_USER", "Grant portal to user."),
    GRANT_PRIVS_TO_USER("usermgr", "GRANT_PRIVS_TO_USER", "Grant privs to user."),
    UNGRANT_PRIVS_TO_USER("usermgr", "UNGRANT_PRIVS_TO_USER", "Ungrant privs to user."),
    GRANT_PORTAL_TO_ROLE("rolemgr", "GRANT_PORTAL_TO_ROLE", "Grant portal to role."),
    UNGRANT_PORTAL_TO_ROLE("rolemgr", "UNGRANT_PORTAL_TO_ROLE", "Ungrant portal to role."),
    GRANT_PRIVILEGE_TO_ROLE("rolemgr", "GRANT_PRIVILEGE_TO_ROLE", "Grant privilege to role."),
    UNGRANT_PRIVILEGE_TO_ROLE("rolemgr", "UNGRANT_PRIVILEGE_TO_ROLE", "Ungrant privilege to role."),
    GRANT_ROLESERVICE_PRIV_TO_ROLE("rolemgr", "GRANT_ROLESERVICE_PRIV_TO_ROLE", "Grant roleService privilege to role."),
    UNGRANT_ROLESERVICE_PRIV_TO_ROLE("rolemgr", "UNGRANT_ROLESERVICE_PRIV_TO_ROLE", "Ungrant roleService privilege to role."),
    DELETE_ROLE("rolemgr", "DELETE_ROLE", "Delete role."),
    ADD_ROLE("rolemgr", "ADD_ROLE", "Add role."),
    UNGRANT_PRIVILEGE_TO_USER("usermgr", "UNGRANT_PRIVILEGE_TO_USER", "Ungrant privilege to user."),
    GRANT_PRIVILEGE_TO_USER("usermgr", "GRANT_PRIVILEGE_TO_USER", "Grant privilege to user."),
    DELETE_PORTAL("portalmgr", "DELETE_PORTAL", "Delete portal"),
    MODIFY_PORTAL("portalmgr", "MODIFY_PORTAL", "Modify portal"),
    ADD_PORTAL("portalmgr", "ADD_PORTAL", "Add portal"),
    ADD_COMPONENT("componentmgr", "ADD_COMPONENT", "Add component"),
    MODIFY_COMPONENT("componentmgr", "MODIFY_COMPONENT", "Modify component"),
    DELETE_COMPONENT("componentmgr", "DELETE_COMPONENT", "Delete component"),
    MODIFY_ROLE("rolemgr", "MODIFY_ROLE", "Modify role"),
    MODIFY_ROLE_SERVICE_PRIV("rolemgr", "MODIFY_ROLE_SERVICE_PRIV", "Modify role service priv"),
    REMOVE_ROLE_SERVICE_CACHE("rolemgr", "REMOVE_ROLE_SERVICE_CACHE", "Remove role service cache"),
    ADD_SITE("siteMgr", "ADD_SITE", "Add site."),
    MODIFY_SITE("siteMgr", "MODIFY_SITE", "Modify site."),
    DELETE_SITE("siteMgr", "DELETE_SITE", "Delete site."),
    ERROR_HIS("errHis", "ERROR_HIS", "ERROR LOG"),
    TRANSACTION("trx", "TRANSACTION", "TRANSACTION LOG"),
    INVALID_CREDENTIAL("usermgr", "INVALID_CREDENTIAL", "Invalid Credential.");

    private String eventType;

    private String eventCode;

    private String comments;

    LogEvent(String eventType, String eventCode, String comments) {
        this.eventType = eventType;
        this.eventCode = eventCode;
        this.comments = comments;
    }

    public String getEventType() {
        return this.eventType;
    }

    public String getEventCode() {
        return this.eventCode;
    }

    public String getComments() {
        return this.comments;
    }


    public static LogEvent getLogEvent(String code) {
        for (LogEvent s : LogEvent.values()) {
            if (s.getEventCode().equals(code)) {
                return s;
            }
        }
        return LogEvent.ERROR_HIS;
    }
}