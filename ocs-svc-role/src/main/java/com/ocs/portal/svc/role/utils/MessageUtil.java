package com.ocs.portal.svc.role.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
public class MessageUtil {

  private static MessageSource messageSource;

  @Autowired
  public MessageUtil(MessageSource messageSource) {
    MessageUtil.messageSource = messageSource;
  }

  public static String getMessage(String key) {
    return messageSource.getMessage(key, null, Locale.getDefault());
  }

//  public static String getMessage(String key, Object[] args) {
//    return messageSource.getMessage(key, args, LocaleContextHolder.getLocale().getDefault());
//  }
}