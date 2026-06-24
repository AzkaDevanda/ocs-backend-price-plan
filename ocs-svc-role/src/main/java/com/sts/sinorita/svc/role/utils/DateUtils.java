package com.sts.sinorita.svc.role.utils;

import io.micrometer.common.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateUtils {

    public static Date formatSQLDate(String date) {
        return formatSQLDateTime(date, "yyyy-MM-dd");
    }

    private static SimpleDateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    public static Date formatSQLDateTime(String date, String format) {
        if (StringUtils.isEmpty(date))
            return null;
        SimpleDateFormat simpleDateFormat = getDateFormat(format);
        try {
            Date tmpDate = simpleDateFormat.parse(date);
            Timestamp time = new Timestamp(tmpDate.getTime());
            return new Date(time.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("the date string " + date + " is not matching format ", e);
        }
    }


    public static String formatTimeString(Date date) {
        return formatString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatString(Date date, String format) {
        if (date == null)
            return null;
        SimpleDateFormat sdf = getDateFormat(format);
        return sdf.format(date);
    }
    public static Date formatDateWithAuto(String date) {
        Date ret = null;
        if (date == null || date.length() == 0)
            return null;
        if (date.length() > 11) {
            if (date.indexOf('-') > 0) {
                if (date.indexOf(':') > 0) {
                    ret = formatDate(date, "yyyy-MM-dd HH:mm:ss");
                } else {
                    ret = formatDate(date, "yyyy-MM-dd HH-mm-ss");
                }
            } else if (date.indexOf('/') > 0) {
                ret = formatDate(date, "yyyy/MM/dd HH:mm:ss");
            } else {
                ret = formatDate(date, "yyyyMMddHHmmss");
            }
        } else if (date.indexOf('-') > 0) {
            ret = formatDate(date, "yyyy-MM-dd");
        } else if (date.length() == 8) {
            ret = formatDate(date, "yyyyMMdd");
        } else {
            ret = formatDate(date, "yyyy");
        }
        return ret;
    }

    public static Date formatDate(String date, String format) {
        if (StringUtils.isEmpty(date))
            return null;
        SimpleDateFormat simpleDateFormat = getDateFormat(format);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("the date string " + date + " is not matching format", e);
        }
    }
}
