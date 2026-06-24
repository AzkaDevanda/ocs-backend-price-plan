package com.sts.sinorita.svc.role.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sts.sinorita.svc.role.dto.request.FilterCondition;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidationService {
  private final EntityManager entityManager;

  public boolean isSameValue(String tableName, Map<String, Object> filters) {
    StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM " + tableName + " WHERE 1=1");

    for (String key : filters.keySet()) {
      sql.append(" AND (:" + key + " IS NULL OR " + key + " = :" + key + ")");
    }

    Query query = entityManager.createNativeQuery(sql.toString());
    for (Map.Entry<String, Object> entry : filters.entrySet()) {
      query.setParameter(entry.getKey(), entry.getValue());
    }

    Number count = (Number) query.getSingleResult();
    return count != null && count.longValue() > 0;
  }

  public boolean isSameValueWithCustomFilters(String tableName, List<FilterCondition> filters, String excludeColumn,
      Object excludeId) {
    StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM " + tableName + " WHERE 1=1");

    // Tambahkan filter normal
    for (int i = 0; i < filters.size(); i++) {
      FilterCondition fc = filters.get(i);
      String paramName = fc.getColumn() + "_" + i; // parameter unique
      String colExpr = fc.getFunction().equalsIgnoreCase("NONE") ? fc.getColumn()
          : fc.getFunction() + "(" + fc.getColumn() + ")";

      switch (fc.getOperator().toUpperCase()) {
        case "=":
          sql.append(" AND (:" + paramName + " IS NULL OR " + colExpr + " = :" + paramName + ")");
          break;
        case "LIKE":
          sql.append(" AND (:" + paramName + " IS NULL OR " + colExpr + " LIKE :" + paramName + ")");
          break;
        case "IN":
          sql.append(" AND " + colExpr + " IN (:" + paramName + ")");
          break;
        default:
          throw new IllegalArgumentException("Unsupported operator: " + fc.getOperator());
      }
    }

    // Tambahkan filter NOT ID jika ada
    if (excludeColumn != null && excludeId != null) {
      sql.append(" AND " + excludeColumn + " <> :excludeId");
    }

    Query query = entityManager.createNativeQuery(sql.toString());

    // Set parameter filter biasa
    for (int i = 0; i < filters.size(); i++) {
      FilterCondition fc = filters.get(i);
      String paramName = fc.getColumn() + "_" + i;
      if (fc.getOperator().equalsIgnoreCase("LIKE")) {
        query.setParameter(paramName, "%" + fc.getValue() + "%");
      } else {
        query.setParameter(paramName, fc.getValue());
      }
    }

    // Set parameter excludeId
    if (excludeColumn != null && excludeId != null) {
      query.setParameter("excludeId", excludeId);
    }

    Number count = (Number) query.getSingleResult();
    return count != null && count.longValue() > 0;
  }

  public boolean isSameValueNotId(String tableName, Map<String, Object> filters, String excludeIdField,
      Object excludeIdValue) {
    StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM " + tableName + " WHERE 1=1");

    for (String key : filters.keySet()) {
      sql.append(" AND (:" + key + " IS NULL OR " + key + " = :" + key + ")");
    }

    // Tambahkan pengecualian ID (not equal)
    if (excludeIdField != null && excludeIdValue != null) {
      sql.append(" AND " + excludeIdField + " <> :" + excludeIdField);
    }

    Query query = entityManager.createNativeQuery(sql.toString());

    // Set parameter filter
    for (Map.Entry<String, Object> entry : filters.entrySet()) {
      query.setParameter(entry.getKey(), entry.getValue());
    }

    // Set parameter exclude jika ada
    if (excludeIdField != null && excludeIdValue != null) {
      query.setParameter(excludeIdField, excludeIdValue);
    }

    Number count = (Number) query.getSingleResult();
    return count != null && count.longValue() > 0;
  }

  public boolean checkReferenceExists(String tableName, Map<String, Object> filters, String excludeField,
      List<Object> excludeValues) {
    StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM " + tableName + " WHERE 1=1");

    // Tambahkan kondisi filter dinamis
    for (String key : filters.keySet()) {
      sql.append(" AND (:" + key + " IS NULL OR " + key + " = :" + key + ")");
    }

    // Tambahkan pengecualian ID jika ada
    if (excludeField != null && excludeValues != null && !excludeValues.isEmpty()) {
      sql.append(" AND " + excludeField + " NOT IN (:excludeValues)");
    }

    Query query = entityManager.createNativeQuery(sql.toString());

    // Set parameter filter
    for (Map.Entry<String, Object> entry : filters.entrySet()) {
      query.setParameter(entry.getKey(), entry.getValue());
    }

    // Set parameter exclude jika ada
    if (excludeField != null && excludeValues != null && !excludeValues.isEmpty()) {
      query.setParameter("excludeValues", excludeValues);
    }

    Number count = (Number) query.getSingleResult();
    return count != null && count.longValue() > 0;
  }

  public Long getNextSequenceValue(String tableName, Map<String, Object> filters, String seqColumn) {
    StringBuilder sql = new StringBuilder("SELECT NVL(MAX(" + seqColumn + "), 0) + 1 FROM " + tableName + " WHERE 1=1");

    // Jika filter ada & tidak kosong → tambahkan kondisi
    if (filters != null && !filters.isEmpty()) {
      for (String key : filters.keySet()) {
        sql.append(" AND (:" + key + " IS NULL OR " + key + " = :" + key + ")");
      }
    }

    Query query = entityManager.createNativeQuery(sql.toString());
    // Set parameter filter jika ada
    if (filters != null && !filters.isEmpty()) {
      for (Map.Entry<String, Object> entry : filters.entrySet()) {
        query.setParameter(entry.getKey(), entry.getValue());
      }
    }

    Number result = (Number) query.getSingleResult();
    return result != null ? result.longValue() : 1L;
  }

  public Long getNextVal(String sequenceName) {
    String sql = "SELECT " + sequenceName + ".NEXTVAL FROM DUAL";
    Query query = entityManager.createNativeQuery(sql);
    Number result = (Number) query.getSingleResult();
    return result != null ? result.longValue() : null;
  }
}
