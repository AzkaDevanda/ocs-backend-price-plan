package com.ocs.portal.svc.role.utils;

public class Const {
    public static final String SUCCESS = "success";

    public static final String FAILED = "failed";

    public static final String SECURITY_LOG = "SECURITY_LOG";

    public static final int CODE_SUC = 0;

    public static final int CODE_FAIL = 1;

    public static final int CODE_AUTH = 2;

    public static final String FAILURE = "failure";

    public static final String FAILURE_CODE = "F";

    public static final String SUCCESS_CODE = "S";

    public static final String AUTHENTICATION = "Your request is not authorized";

    public static final String DATA_LIST = "data_list";

    public static final String COUNT = "count";

    public static final String TOTAL_COUNT = "total_count";

    public static final String STATE_EFF = "A";

    public static final String STATE_INACTIVE = "I";

    public static final String STATE_EXP = "X";

    public static final String USER_LOCKED = "Y";

    public static final String USER_UNLOCKED = "N";

    public static final String USER_STATE_EFF = "A";

    public static final String USER_STATE_EXP = "X";

    public static final String USER_STATE_IN_ACTIVE = "I";

    public static final String USER_STATE_TEMPORARY = "T";

    public static final String USER_STATE_FROZEN = "F";

    public static final String USER_STATE_DEL = "D";

    public static final String ONLINE_LOCK = "L";

    public static final String PRIV_TYPE_MENU = "M";

    public static final String PRIV_TYPE_COMPONENT = "C";

    public static final String PRIV_TYPE_PORTLET = "P";

    public static final String PRIV_TYPE_SERVICE = "R";

    public static final String PRIV_TYPE_INDEPENDENT = "I";

    public static final String PRIV_STATE_EFF = "A";

    public static final String PRIV_STATE_EXP = "X";

    public static final String PORTAL_STATE_EFF = "A";

    public static final String PORTAL_STATE_EXP = "X";

    public static final String MENU_ICON_URL = "";

    public static final String IS_LOCKED_TRUE = "Y";

    public static final String IS_LOCKED_FALSE = "N";

    public static final String PORTAL_TYPE_MENU = "1";

    public static final String PORTAL_TYPE_DIR = "0";

    public static final String USER_HIS_LOCKED = "Lock User";

    public static final String USER_HIS_UNLOCKED = "Unlock User";

    public static final String USER_HIS_ADD_USER = "Add User";

    public static final String USER_HIS_DEL_USER = "Delete User";

    public static final String USER_HIS_DISABLE_USER = "Disable User";

    public static final String USER_HIS_ENABLE_USER = "Enable User";

    public static final String USER_HIS_MOD_PWD = "Modify Password";

    public static final String USER_HIS_MOD_INFO = "Modify User Information";

    public static final String USER_HIS_MOD_DEFAULT_PORTAL = "Modify Default Portal";

    public static final String USER_HIS_REST_PWD = "Reset Password";

    public static final String IS_SUC = "is_success";

    public static final String FALSE = "false";

    public static final String TRUE = "true";

    public static final String DESC = "description";

    public static final String ERROR_CODE = "errCode";

    public static final String EVENT_SRC_FRAMEWORK = "ff";

    public static final String EVENT_TYPE_LOGIN = "login";

    public static final String EVENT_TYPE_LOGOUT = "logout";

    public static final String EVENT_TYPE_ADMIN = "system admin";

    public static final String EVENT_TYPE_AUDIT = "security audit";

    public static final String EVENT_TYPE_PARAM = "system params";

    public static final String EVENT_PAGE_LOG = "page log";

    public static final String EVENT_TYPE_INVOKE = "invoke";

    public static final String EVENT_TYPE_DUBBO_CLIENT = "dubbo client";

    public static final String EVENT_TYPE_DUBBO_SERVER = "dubbo server";

    public static final String EVENT_TYPE_MENU_OPEN = "MENU_OPEN";

    public static final String EVENT_TYPE_BUTTON_CLICK = "BUTTON_CLICK";

    public static final String EVENT_CODE_LOGIN_SUC = "LOGIN_SUCCESS";

    public static final String EVENT_CODE_LOGIN_EXC = "LOGIN_EXCEPTION";

    public static final String EVENT_CODE_LOGIN_TRY = "LOGIN_TRY";

    public static final String EVENT_CODE_LOGOUT_NOR = "LOGOUT_NORMAL";

    public static final String EVENT_CODE_LOGOUT_TIMEOUT = "LOGOUT_TIMEOUT";

    public static final String EVENT_CODE_LOGOUT_FORCE = "LOGOUT_FORCE";

    public static final String EVENT_CODE_URL_AUDIT = "URL_AUDIT_FAIL";

    public static final String EVENT_CODE_PARAM_MODIFY = "PARAM_MODIFY";

    public static final String EVENT_CODE_PAGE_LOG = "PAGE_LOG";

    public static final String EVENT_CODE_UNLOCK_AUTO = "UNLOCK_AUTO";

    public static final String EVENT_CODE_DUBBO_CLIENT_SUC = "CLIENT_SUCCESS";

    public static final String EVENT_CODE_DUBBO_CLIENT_EXC = "CLIENT_EXCEPTION";

    public static final String EVENT_CODE_DUBBO_SERVER_SUC = "SERVER_SUCCESS";

    public static final String EVENT_CODE_DUBBO_SERVER_EXC = "SERVER_EXCEPTION";

    public static final String EVENT_CODE_MENU_OPEN = "MENU_OPEN";

    public static final String EVENT_CODE_BUTTON_CLICK = "BUTTON_CLICK";

    public static final int EVENT_SUCCESS = 0;

    public static final int EVENT_FAIL = 1;

    public static final String EVENT_CODE_SECURITY_RULE_MODIFY = "SECURITY_RULE_MODIFY";

    public static final String TRANSLATE = "translate";

    public static final String JSON_PATH = "JSON_PATH";

    public static final String REQ_URI = "REQ_URI";

    public static final String WIDGETS = "widgets";

    public static final String SERV_LOG_USER = "Z";

    public static final String SERV_LOG_STAFF = "F";

    public static final String SERV_LOG_STAFFJOB = "A";

    public static String realRootPath;

    public static String hostIp;

    public static String projCode;

    public static String FORCE_LOGIN = "NO";

    public enum ThreadLocalVariable {
        USER_ID, USER_CODE, USER_NAME, LOGIN_IP, STAFF_ID, STAFF_CODE, STAFF_NAME, STAFF_JOB_ID, DEFAULT_ORG_ID, DEFAULT_ORG_CODE, ORG_ID, ORG_NAME, AREA_ID, AREA_NAME, JOB_ID, SP_ID, SP_NAME, JOB_CODE, ORG_CODE, AREA_CODE, LOGIN_FAIL, LOGIN_DATE, EMAIL, PHONE, IS_ADMIN, ACCESS_TOKEN, REFRESH_TOKEN, AZURE_AD_TOKEN, RATE_PERIOD, RATE_COUNT;
    }

    public static String version = "9.3.0";

    public static final String ZCACHE_PREFIX = "NGPortal";

    public static final String SESSION_KEY = "SESSION_KEY";

    public static final String SESSION_USER = "LOGIN_USER";

    public static final String CACHE_ENABLED_DEFULT = "ftf.cache.enabled=false";

    public static final String MQ_ENABLED_DEFULT = "ftf.zcm.zmq-enabled=false";

    public static final String OPEN_SSO_MODE = "OPEN_SSO_MODE";

    public static final String SSO_ADDRESS = "SSO_ADDRESS";

    public static final String DEFAULT_PORTAL_NAME = "IT Center Administrator Portal";

    public static final String SSO_SYN_PORTAL_NAME = "SSO_SYN_PORTAL_NAME";

    public static final String LOG_AUDIT_EVENT_CODES = "LOGIN_SUCCESS|LOGOUT_NORMAL|LOGIN_TRY|ADD_USER|MODIFY_USER|DELETE_USER|LOCK_USER|UNLOCK_USER|ENABLE_USER|DISABLE_USER|MODIFY_PASSWORD|RESET_PASSWORD|GRANT_ROLE_TO_USER|UNGRANT_ROLE_TO_USER|URL_AUDIT_FAIL|";

    public static final String LOG_AUDIT_EVENT_CODES_RES = "Login Success|Logout Normal|Login Try|Add User|Modify User|Delete User|Lock User|Unlock User|Enable User|Disable User|Modify Password|Reset Password|Grant role To User|Ungrant role To User|Url Audit Fail|";

    public static final String EMAIL_FORMAT_PASSWORD = "EMAIL_FORMAT_PASSWORD";

    public static final String LOGIN_SESSION_ID_SET = "LOGIN_SESSION_ID_SET";

    public static final String LOGIN_SESSION_ID_USER_SET = "LOGIN_SESSION_ID_SET:";

    public static final String UC_REQUEST_PREFIX = "/wcs/uc";

    public static final String CMS_REQUEST_PREFIX = "/wcs/cms";

    public static final String ACTION_ADD = "ADD";

    public static final String ACTION_MODIFY = "MODIFY";

    public static final String MENU_TYPE_PAGE = "A";

    public static final String MENU_TYPE_REMOTE = "T";

    public static final String MENU_TYPE_LOCAL = "L";

    public static final String MENU_TYPE_REACT = "R";

    public static final String COMMON_Y = "Y";

    public static final String COMMON_N = "N";

    public static final String OAUTH2_ERROR_CODE = "error_code";

    public static final String SEND_USER_EMAIL_TOKEN = "Portal:ForgetPassword:EmailToken:";
}
