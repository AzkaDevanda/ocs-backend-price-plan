package com.ocs.portal.utils;

import com.ocs.portal.dto.response.PricePlanResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@Slf4j
public class Hellper {

    public Page<PricePlanResponseDto> convertListToPageable(List<PricePlanResponseDto> objectList, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Calculate the start and end indices based on pagination
        int start = Math.min((int) pageable.getOffset(), objectList.size());
        int end = Math.min((start + pageable.getPageSize()), objectList.size());

        List<PricePlanResponseDto> pagedList = objectList.subList(start, end);

        return new PageImpl<>(pagedList, pageable, objectList.size());
    }


    public LocalDateTime StringToLocalDateTime(String dateTimeString) {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            try {
                 localDateTime = LocalDateTime.parse(dateTimeString, formatter);
                System.out.println("LocalDateTime: " + localDateTime);
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing date and time string: " + e.getMessage());
            }
            return localDateTime;
    }

    public LocalDate StringToLocalDate(String dateTimeString) {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            localDate = LocalDate.parse(dateTimeString, formatter);
            System.out.println("LocalDateTime: " + localDate);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date and time string: " + e.getMessage());
        }
        return localDate;
    }

    public String getStringFromByte(byte[] byteArray){
        return new String(byteArray, StandardCharsets.UTF_8);
    }

    public int toInt(Object obj) {
        if (obj instanceof BigDecimal bd) {
            return bd.intValue();
        } else if (obj instanceof Integer i) {
            return i;
        } else if (obj instanceof Long l) {
            return l.intValue();
        } else if (obj instanceof Short s) {
            return s.intValue();
        } else if (obj != null) {
            return Integer.parseInt(obj.toString());
        } else {
            return 0;
        }
    }

    public static Date offsetYear(Date date, int years) {
        if (date == null)
            return null;
        Date newdate = (Date)date.clone();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newdate);
        calendar.add(1, years);
        newdate.setTime(calendar.getTimeInMillis());
        return newdate;
    }

    public static Date offsetMonth(Date date1, int months) {
        if (date1 == null)
            return null;
        Date date = new Date(date1.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int curDay = calendar.get(5);
        int maxDay = calendar.getActualMaximum(5);
        calendar.set(5, 1);
        calendar.add(2, months);
        int newMaxDay = calendar.getActualMaximum(5);
        if (curDay == maxDay) {
            calendar.set(5, newMaxDay);
        } else if (curDay > newMaxDay) {
            calendar.set(5, newMaxDay);
        } else {
            calendar.set(5, curDay);
        }
        date.setTime(calendar.getTimeInMillis());
        return date;
    }

    public static Date offsetWeek(Date date, int weeks) {
        return offsetDay(date, 7 * weeks);
    }

    public static Date offsetDay(Date date, int days) {
        return offsetHour(date, (24 * days));
    }

    public static Date offsetHour(Date date, long hours) {
        return offsetMinute(date, 60L * hours);
    }

    public static Date offsetMinute(Date date, long minutes) {
        return offsetSecond(date, 60L * minutes);
    }

    public static Date offsetSecond(Date date, long seconds) {
        if (date == null)
            return null;
        long time = date.getTime();
        long time2 = time + seconds * 1000L;
        long time3 = time + seconds * 1000L - 3600000L;
        Date date2 = new Date(time2);
        Date date3 = new Date(time3);
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);
        Calendar calendarDate2 = Calendar.getInstance();
        calendarDate2.setTime(date2);
        Calendar calendarDate3 = Calendar.getInstance();
        calendarDate3.setTime(date3);
        long dstDate = calendarDate.get(16);
        long dstDate2 = calendarDate2.get(16);
        long dstDate3 = calendarDate3.get(16);
        long dstOffset = dstDate - dstDate2;
        if (dstOffset == 0L || (dstDate2 - dstDate3 != 0L && dstDate2 != 0L))
            return date2;
        return new Date(time2 + dstOffset);
    }


    public static long differDateInDays(Date beginDate, Date endDate, int returnType) {
        long begin = beginDate.getTime();
        long end = endDate.getTime();
        long surplus = begin - end;
        Calendar calendarBeginDate = Calendar.getInstance();
        calendarBeginDate.setTime(beginDate);
        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.setTime(endDate);
        long dstOffset = (calendarBeginDate.get(16) - calendarEndDate.get(16));
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
        }
        return ret;
    }

}
