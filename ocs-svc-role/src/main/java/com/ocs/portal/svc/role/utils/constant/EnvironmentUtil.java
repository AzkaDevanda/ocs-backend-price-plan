package com.ocs.portal.svc.role.utils.constant;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

public abstract class EnvironmentUtil {


    private static final Environment env = SpringContext.getBean(Environment.class);

    public static String getValue(String property, String defaultValue) {
        return StringUtils.isBlank(property) ? defaultValue : env.getProperty(property, defaultValue);
    }

    public static String getValue(String property) {
        return getValue(property, null);
    }
}
