package com.sts.sinorita.util;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneRules;
import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class DateUtil {
  static Logger logger = LoggerFactory.getLogger(DateUtil.class);

  private static JdbcTemplate jdbcTemplate;

  @Autowired
  public DateUtil(JdbcTemplate jdbcTemplate) {
    DateUtil.jdbcTemplate = jdbcTemplate;
  }

  public static String dateTime2String(LocalDateTime dateTime) {
    return dateTime2String(dateTime, "yyyy-MM-dd");
  }

  public static String dateTime2String(LocalDateTime dateTime, String format) {
    if (dateTime == null) {
      return "";
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    return dateTime.format(formatter);
  }

  public static long differDateInDays(LocalDateTime beginDate, LocalDateTime endDate, int returnType) {
    if (beginDate == null || endDate == null) {
      return 0L;
    }

    ZoneId zoneId = ZoneId.systemDefault();

    // Convert LocalDateTime → Instant (millis)
    long begin = beginDate.atZone(zoneId).toInstant().toEpochMilli();
    long end = endDate.atZone(zoneId).toInstant().toEpochMilli();

    long surplus = begin - end;

    // === DST OFFSET (equivalent Calendar.get(16)) ===
    ZoneRules rules = zoneId.getRules();
    int beginDstOffset = (int) (rules.getDaylightSavings(
        beginDate.atZone(zoneId).toInstant()).getSeconds() * 1000);

    int endDstOffset = (int) (rules.getDaylightSavings(
        endDate.atZone(zoneId).toInstant()).getSeconds() * 1000);

    long dstOffset = beginDstOffset - endDstOffset;
    surplus += dstOffset;

    long ret = 0L;
    switch (returnType) {
      case 0:
        ret = surplus / 1000L;
        break;
      case 1:
        ret = surplus / 1000L / 60L;
        break;
      case 2:
        ret = surplus / 1000L / 60L / 60L;
        break;
      case 3:
        ret = surplus / 1000L / 60L / 60L / 24L;
        break;
      default:
        break;
    }
    return ret;
  }

  public static LocalDateTime offsetSecond(
      LocalDateTime date,
      long seconds) {
    if (date == null) {
      return null;
    }

    ZoneId zoneId = ZoneId.systemDefault();

    ZonedDateTime zdt = date.atZone(zoneId);

    ZonedDateTime zdt2 = zdt.plusSeconds(seconds);
    ZonedDateTime zdt3 = zdt.plusSeconds(seconds).minusHours(1);

    // DST offset (milliseconds)
    long dstDate = zdt.getOffset().getTotalSeconds() * 1000L;
    long dstDate2 = zdt2.getOffset().getTotalSeconds() * 1000L;
    long dstDate3 = zdt3.getOffset().getTotalSeconds() * 1000L;

    long dstOffset = dstDate - dstDate2;

    if (dstOffset == 0L ||
        (dstDate2 - dstDate3 != 0L && dstDate2 != 0L)) {
      return zdt2.toLocalDateTime();
    }

    return zdt2.plus(Duration.ofMillis(dstOffset)).toLocalDateTime();
  }

  public static String date2String(LocalDateTime dateTime, String format) {
    if (dateTime == null) {
      return "";
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    return dateTime.format(formatter);
  }

  public static SimpleDateFormat getDateFormat(String format) {
    return new SimpleDateFormat(format);
  }

  // public static Date GetDBDateTime() {
  // Date date = new Date();
  // Session session = SessionContext.currentSession();
  // try {
  // session.beginTrans();
  // date = GetDBDateTime(false);
  // session.commitTrans();
  // } finally {
  // session.releaseTrans();
  // }
  // }
  // return date;

  @Transactional(readOnly = true)
  public static LocalDateTime GetDBDateTime() {
    return jdbcTemplate.queryForObject(
        "SELECT CURRENT_TIMESTAMP FROM DUAL",
        LocalDateTime.class);
  }

  public static LocalDateTime string2SQLDate(String date, String format) {
    boolean isSucc = true;
    Exception operateException = null;

    if (format == null || format.isBlank()) {
      isSucc = false;
      operateException = new IllegalArgumentException("the date format string is null!");
    }

    DateTimeFormatter formatter = null;
    try {
      formatter = DateTimeFormatter.ofPattern(format);
    } catch (Exception e) {
      isSucc = false;
      operateException = new IllegalArgumentException(
          "the date format string is not matching available format object", e);
    }

    LocalDateTime tmpDate = null;
    try {
      if (isSucc) {
        tmpDate = LocalDateTime.parse(date, formatter);
      }
    } catch (Exception e) {
      isSucc = false;
      operateException = e;
    }

    if (!isSucc) {
      logger.error(
          "the date string {} is not matching format: {}",
          date, format, operateException);

      if (operateException instanceof IllegalArgumentException) {
        throw (IllegalArgumentException) operateException;
      }

      throw new IllegalArgumentException(
          "the date string " + date +
              " is not matching format: " + format +
              ".\n cause by :" + operateException);
    }

    return tmpDate;
  }

  public static LocalDateTime string2SQLDate(String date) {
    LocalDateTime ret = null;
    if (date == null || date.length() == 0)
      return ret;
    if (date.length() > 11) {
      if (date.indexOf('-') > 0) {
        if (date.indexOf(':') > 0) {
          ret = string2SQLDate(date, "yyyy-MM-dd HH:mm:ss");
        } else {
          ret = string2SQLDate(date, "yyyy-MM-dd HH-mm-ss");
        }
      } else if (date.indexOf('/') > 0) {
        ret = string2SQLDate(date, "yyyy/MM/dd HH:mm:ss");
      } else {
        ret = string2SQLDate(date, "yyyyMMddHHmmss");
      }
    } else if (date.indexOf('-') > 0) {
      ret = string2SQLDate(date, "yyyy-MM-dd");
    } else if (date.length() == 8) {
      ret = string2SQLDate(date, "yyyyMMdd");
    } else {
      ret = string2SQLDate(date, "yyyy");
    }
    return ret;
  }

  public static LocalDateTime getFullDate(LocalDateTime input) {
    if (input == null) {
      return null;
    }
    return input
        .withHour(0)
        .withMinute(0)
        .withSecond(0)
        .withNano(0);
  }

  public static int compare(LocalDateTime beginDate, LocalDateTime endDate) {
    if (beginDate == null || endDate == null) {
      throw new IllegalArgumentException("Date must not be null");
    }

    if (beginDate.isAfter(endDate)) {
      return 2;
    }
    if (beginDate.isEqual(endDate)) {
      return 1;
    }
    return 0;
  }

  public static LocalDateTime offsetMonth(LocalDateTime dateTime, int months) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.plusMonths(months);
  }

  public static Date getNowDate() {
    return new Date((new Date()).getTime());
  }

  public static LocalDateTime offsetMinute(LocalDateTime date, long minutes) {
    return offsetSecond(date, 60L * minutes);
  }

  public static LocalDateTime offsetHour(LocalDateTime date, long hours) {
    return offsetMinute(date, 60L * hours);
  }

  public static LocalDateTime offsetDay(LocalDateTime date, int days) {
    return offsetHour(date, (24L * days));
  }
}
