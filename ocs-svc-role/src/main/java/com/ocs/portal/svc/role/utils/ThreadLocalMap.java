package com.ocs.portal.svc.role.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ThreadLocalMap {

    @Autowired
    private static ObjectMapper objectMapper;

    private static ThreadLocal<Map<String, Object>> currentSession = new InheritableThreadLocal<Map<String, Object>>() {
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }

        protected Map<String, Object> childValue(Map<String, Object> parentValue) {
            return objectMapper.convertValue(parentValue, new TypeReference<Map<String, Object>>() {});
        }
    };


    @Autowired
    public static void setObjectMapper(ObjectMapper objectMapper) {
        ThreadLocalMap.objectMapper = objectMapper;
    }

    public static void set(String key, Object value) {
        Map<String, Object> session = currentSession.get();
        if (session != null)
            session.put(key, value);
    }

    public static void remove(String key) {
        Map<String, Object> session = currentSession.get();
        if (session != null)
            session.remove(key);
    }

    public static Object get(String key) {
        Map<String, Object> session = currentSession.get();
        if (session != null)
            return session.get(key);
        return null;
    }

    public static Long getLong(String key) {
        Map<String, Object> session = currentSession.get();
        if (session != null && session.get(key) != null)
            return Long.valueOf(Long.parseLong(session.get(key).toString()));
        return null;
    }

    public static String getString(String key) {
        Map<String, Object> session = currentSession.get();
        if (session != null && session.get(key) != null)
            return session.get(key).toString();
        return null;
    }

    public static Long getSpId() {
        Long spId = getLong(Const.ThreadLocalVariable.SP_ID.name());
        return (spId == null) ? Long.valueOf(0L) : spId;
    }

    public static String getSpName() {
        return getString(Const.ThreadLocalVariable.SP_NAME.name());
    }

    public static Long getLoginFail() {
        return getLong(Const.ThreadLocalVariable.LOGIN_FAIL.name());
    }

    public static Long getUserId() {
        return getLong(Const.ThreadLocalVariable.USER_ID.name());
    }

    public static String getEmail() {
        return getString(Const.ThreadLocalVariable.EMAIL.name());
    }

    public static String getPhone() {
        return getString(Const.ThreadLocalVariable.PHONE.name());
    }

    public static String getLoginIp() {
        return getString(Const.ThreadLocalVariable.LOGIN_IP.name());
    }

    public static Long getStaffId() {
        return getLong(Const.ThreadLocalVariable.STAFF_ID.name());
    }

    public static Long getStaffJobId() {
        return getLong(Const.ThreadLocalVariable.STAFF_JOB_ID.name());
    }

    public static Long getDefaultOrgId() {
        return getLong(Const.ThreadLocalVariable.DEFAULT_ORG_ID.name());
    }

    public static String getDefaultOrgCode() {
        return getString(Const.ThreadLocalVariable.DEFAULT_ORG_CODE.name());
    }

    public static String getUserCode() {
        return getString(Const.ThreadLocalVariable.USER_CODE.name());
    }

    public static String getUserName() {
        return getString(Const.ThreadLocalVariable.USER_NAME.name());
    }

    public static Long getOrgId() {
        return getLong(Const.ThreadLocalVariable.ORG_ID.name());
    }

    public static String getOrgName() {
        return getString(Const.ThreadLocalVariable.ORG_NAME.name());
    }

    public static Long getAreaId() {
        return getLong(Const.ThreadLocalVariable.AREA_ID.name());
    }

    public static Long getJobId() {
        return getLong(Const.ThreadLocalVariable.JOB_ID.name());
    }

    public static String getAreaName() {
        return getString(Const.ThreadLocalVariable.AREA_NAME.name());
    }

    public static String getStaffName() {
        return getString(Const.ThreadLocalVariable.STAFF_NAME.name());
    }

    public static String getOrgCode() {
        return getString(Const.ThreadLocalVariable.ORG_CODE.name());
    }

    public static String getAreaCode() {
        return getString(Const.ThreadLocalVariable.AREA_CODE.name());
    }

    public static String getJobCode() {
        return getString(Const.ThreadLocalVariable.JOB_CODE.name());
    }

    public static String getStaffCode() {
        return getString(Const.ThreadLocalVariable.STAFF_CODE.name());
    }

    public static Boolean isAdmin() {
        String isAdmin = getString(Const.ThreadLocalVariable.IS_ADMIN.name());
        return Boolean.valueOf("TRUE".equalsIgnoreCase(isAdmin));
    }

    public static void clear() {
        currentSession.remove();
    }
}
