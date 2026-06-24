package com.sts.sinorita.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerPortal {

    public void loggerInfo(Class<?> clazz, String msg) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info(msg);
    }

    public void loggerDebug(Class<?> clazz, String msg) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.debug(msg);
    }
}
