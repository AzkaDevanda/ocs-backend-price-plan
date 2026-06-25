package com.ocs.portal.svc.role.utils;

import java.lang.reflect.Field;

public class StringSanitizer {

    public static <T> T sanitize(T obj) {
        if (obj == null) return null;

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value == null  || "string".equalsIgnoreCase(value.toString())) {
                        field.set(obj, null);
                    }
                } catch (IllegalAccessException ignored) {}
            }
        }
        return obj;
    }
}
