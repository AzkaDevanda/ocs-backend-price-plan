package com.ocs.portal.svc.role.utils.helper;

import org.apache.poi.ss.usermodel.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelHelper {

  private static final List<DateTimeFormatter> DATE_FORMATS = List.of(
      DateTimeFormatter.ofPattern("yyyy-MM-dd"),
      DateTimeFormatter.ofPattern("dd-MM-yyyy"),
      DateTimeFormatter.ofPattern("dd/MM/yyyy"),
      DateTimeFormatter.ofPattern("MM/dd/yyyy"),
      DateTimeFormatter.ofPattern("ddMMyyyy"));

  public static Object getCellValue(Cell cell) {
    if (cell == null)
      return null;
    try {
      switch (cell.getCellType()) {
        case STRING:
          return cell.getStringCellValue().trim();
        case BOOLEAN:
          return cell.getBooleanCellValue();
        case NUMERIC:
          if (DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue();
          } else {
            double d = cell.getNumericCellValue();
            if (d == Math.floor(d))
              return (long) d;
            return d;
          }
        case FORMULA:
          FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
          CellValue evaluated = evaluator.evaluate(cell);
          if (evaluated == null)
            return null;
          switch (evaluated.getCellType()) {
            case STRING:
              return evaluated.getStringValue();
            case BOOLEAN:
              return evaluated.getBooleanValue();
            case NUMERIC:
              if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue();
              }
              double dv = evaluated.getNumberValue();
              if (dv == Math.floor(dv))
                return (long) dv;
              return dv;
            default:
              return null;
          }
        default:
          return null;
      }
    } catch (Exception e) {
      return null;
    }
  }

  public static String getString(Cell cell) {
    Object v = getCellValue(cell);
    return v == null ? null : String.valueOf(v).trim();
  }

  public static Long getLong(Cell cell) {
    Object v = getCellValue(cell);
    if (v == null)
      return null;
    try {
      return Long.parseLong(String.valueOf(v));
    } catch (Exception e) {
      try {
        Double d = Double.parseDouble(String.valueOf(v));
        return d.longValue();
      } catch (Exception ex) {
        return null;
      }
    }
  }

  public static Integer getInt(Cell cell) {
    Long l = getLong(cell);
    return l == null ? null : l.intValue();
  }

  public static LocalDate getDate(Cell cell) {
    if (cell == null)
      return null;
    try {
      if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
        return cell.getLocalDateTimeCellValue().toLocalDate();
      }
      if (cell.getCellType() == CellType.STRING) {
        String text = cell.getStringCellValue().trim();
        if (text.isEmpty())
          return null;
        for (DateTimeFormatter f : DATE_FORMATS) {
          try {
            return LocalDate.parse(text, f);
          } catch (Exception ignored) {
          }
        }
        try {
          return LocalDate.parse(text); // ISO fallback
        } catch (Exception ignored) {
        }
      }
    } catch (Exception e) {
      return null;
    }
    return null;
  }

  public static LocalDateTime getDateTime(Cell cell) {
    if (cell == null)
      return null;
    try {
      if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
        return cell.getLocalDateTimeCellValue();
      }
      if (cell.getCellType() == CellType.STRING) {
        String text = cell.getStringCellValue().trim();
        if (text.isEmpty())
          return null;

        List<DateTimeFormatter> dtfs = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
        for (DateTimeFormatter f : dtfs) {
          try {
            return LocalDateTime.parse(text, f);
          } catch (Exception ignored) {
          }
        }

        LocalDate d = getDate(cell);
        return d == null ? null : d.atStartOfDay();
      }
    } catch (Exception e) {
      return null;
    }
    return null;
  }

  public static LocalDate getLocalDate(Cell cell) {
    if (cell == null)
      return null;

    if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
      return cell.getDateCellValue()
          .toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
    }

    try {
      return LocalDate.parse(cell.getStringCellValue().trim());
    } catch (Exception e) {
      return null;
    }
  }

  public static LocalDateTime getDateAtStartOfDay(Cell cell) {
    LocalDate d = getDate(cell);
    return d == null ? null : d.atStartOfDay();
  }

  // read header indexes (lowercased)
  public static Map<String, Integer> getHeaderIndexes(Sheet sheet) {
    Row headerRow = sheet.getRow(sheet.getFirstRowNum());
    Map<String, Integer> map = new HashMap<>();
    if (headerRow == null)
      return map;
    for (int i = 0; i < headerRow.getLastCellNum(); i++) {
      Cell c = headerRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
      String name = getString(c);
      if (name != null && !name.isBlank())
        map.put(name.trim().toLowerCase(), i);
    }
    return map;
  }

  public static List<Row> readSheetRows(Sheet sheet) {
    List<Row> rows = new ArrayList<>();
    int first = sheet.getFirstRowNum() + 1;
    int last = sheet.getLastRowNum();
    for (int i = first; i <= last; i++) {
      Row r = sheet.getRow(i);
      if (r == null)
        continue;
      boolean empty = true;
      for (int c = 0; c < r.getLastCellNum(); c++) {
        if (getCellValue(r.getCell(c)) != null) {
          empty = false;
          break;
        }
      }
      if (!empty)
        rows.add(r);
    }
    return rows;
  }

  public static boolean validateHeaders(Map<String, Integer> headerMap, String... required) {
    for (String r : required) {
      if (!headerMap.containsKey(r.toLowerCase()))
        return false;
    }
    return true;
  }

  public static String safe(Object obj) {
    return obj == null ? "" : obj.toString();
  }

  public static String safe(LocalDate date, DateTimeFormatter formatter) {
    return date == null ? "" : date.format(formatter);
  }

  public static String safe(LocalDateTime dateTime, DateTimeFormatter formatter) {
    return dateTime == null ? "" : dateTime.format(formatter);
  }

}