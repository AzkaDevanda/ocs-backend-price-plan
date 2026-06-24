package com.sts.sinorita.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialClob;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Clob;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class CommonMapperHelper {

  private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  // ========================
  // 🔢 Long ↔ Integer
  // ========================
  @Named("longToInteger")
  public static Integer longToInteger(Long value) {
    return value != null ? value.intValue() : null;
  }

  @Named("integerToLong")
  public static Long integerToLong(Integer value) {
    return value != null ? value.longValue() : null;
  }

  // ========================
  // 🕒 String ↔ LocalDateTime
  // ========================
  @Named("stringToLocalDateTime")
  public static LocalDateTime stringToLocalDateTime(String value) {
    return value != null ? LocalDateTime.parse(value, DATETIME_FORMATTER) : null;
  }

  @Named("localDateTimeToString")
  public static String localDateTimeToString(LocalDateTime value) {
    return value != null ? value.format(DATETIME_FORMATTER) : null;
  }

  // ========================
  // 📅 String ↔ LocalDate
  // ========================
  @Named("stringToLocalDate")
  public static LocalDate stringToLocalDate(String value) {
    return value != null ? LocalDate.parse(value, DATE_FORMATTER) : null;
  }

  @Named("localDateToString")
  public static String localDateToString(LocalDate value) {
    return value != null ? value.format(DATE_FORMATTER) : null;
  }

  // ========================
  // ⏰ Date ↔ LocalDateTime
  // ========================
  @Named("dateToLocalDateTime")
  public static LocalDateTime dateToLocalDateTime(Date date) {
    return date != null ? Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
  }

  @Named("localDateTimeToDate")
  public static Date localDateTimeToDate(LocalDateTime ldt) {
    return ldt != null ? Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()) : null;
  }

  // ========================
  // ✅ Boolean ↔ Integer
  // ========================
  @Named("booleanToInteger")
  public static Integer booleanToInteger(Boolean value) {
    return value != null ? (value ? 1 : 0) : null;
  }

  @Named("integerToBoolean")
  public static Boolean integerToBoolean(Integer value) {
    return value != null ? value == 1 : null;
  }

  // ========================
  // 💰 BigDecimal ↔ Double
  // ========================
  @Named("bigDecimalToDouble")
  public static Double bigDecimalToDouble(BigDecimal value) {
    return value != null ? value.doubleValue() : null;
  }

  @Named("doubleToBigDecimal")
  public static BigDecimal doubleToBigDecimal(Double value) {
    return value != null ? BigDecimal.valueOf(value) : null;
  }

  // ========================
  // 🧩 Enum ↔ String (generic)
  // ========================
  @Named("enumToString")
  public static String enumToString(Enum<?> e) {
    return e != null ? e.name() : null;
  }

  @Named("stringToEnum")
  public static <E extends Enum<E>> E stringToEnum(String name, Class<E> enumClass) {
    return (name != null && enumClass != null) ? Enum.valueOf(enumClass, name) : null;
  }

  // ========================
  // 🕓 Timestamp ↔ LocalDateTime
  // ========================
  @Named("timestampToLocalDateTime")
  public static LocalDateTime timestampToLocalDateTime(Timestamp ts) {
    return ts != null ? ts.toLocalDateTime() : null;
  }

  @Named("localDateTimeToTimestamp")
  public static Timestamp localDateTimeToTimestamp(LocalDateTime ldt) {
    return ldt != null ? Timestamp.valueOf(ldt) : null;
  }

  @Named("clobToString")
  static String clobToString(Clob clob) {
    if (clob == null)
      return null;

    try (Reader reader = clob.getCharacterStream(); StringWriter writer = new StringWriter()) {
      char[] buffer = new char[2048];
      int len;
      while ((len = reader.read(buffer)) != -1) {
        writer.write(buffer, 0, len);
      }
      return writer.toString();
    } catch (Exception e) {
      throw new RuntimeException("Failed to convert CLOB to String", e);
    }
  }

    @Named("stringToClob")
    static Clob stringToClob(String value) {
        if (value == null)
            return null;

        try {
            return new SerialClob(value.toCharArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert String to CLOB", e);
        }
    }

  // ========================
  // 🧱 String ↔ byte[] (BLOB)
  // ========================
  @Named("stringToBytes")
  public static byte[] stringToBytes(String value) {
    return value != null ? value.getBytes(StandardCharsets.UTF_8) : null;
  }

  @Named("bytesToString")
  public static String bytesToString(byte[] value) {
    return value != null ? new String(value, StandardCharsets.UTF_8) : null;
  }
}