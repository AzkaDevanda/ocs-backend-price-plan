package com.sts.sinorita;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

  public boolean isSameValueV2(String tableName, Map<String, Object> filters) {
    if (filters == null || filters.isEmpty()) {
        throw new IllegalArgumentException("Filters must not be empty");
    }

    StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM " + tableName + " WHERE 1=1");
    for (String key : filters.keySet()) {
      sql.append(" AND ").append(key).append(" = :").append(key);
    }

    Query query = entityManager.createNativeQuery(sql.toString());
    for (Map.Entry<String, Object> entry : filters.entrySet()) {
      if (entry.getValue() == null) {
        throw new IllegalArgumentException("Filter value for " + entry.getKey() + " must not be null");
      }
      query.setParameter(entry.getKey(), entry.getValue());
    }

    Number count = (Number) query.getSingleResult();
    return count.longValue() > 0;
  }

  public boolean isSameValueNotId(String tableName, Map<String, Object> filters, String excludeIdField, Object excludeIdValue) {
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

  public boolean isSameValueNotIdV2(String tableName, Map<String, Object> filters, String excludeIdField, Object excludeIdValue) {
    if (filters == null || filters.isEmpty()) {
      throw new IllegalArgumentException("Filters must not be empty");
    }

    StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM " + tableName + " WHERE 1=1");
    for (String key : filters.keySet()) {
      sql.append(" AND ").append(key).append(" = :").append(key);
    }

    if (excludeIdField != null && excludeIdValue != null) {
      sql.append(" AND ").append(excludeIdField).append(" <> :excludeId");
    }

    Query query = entityManager.createNativeQuery(sql.toString());
    for (Map.Entry<String, Object> entry : filters.entrySet()) {
      if (entry.getValue() == null) {
        throw new IllegalArgumentException("Filter value for " + entry.getKey() + " must not be null");
      }
      query.setParameter(entry.getKey(), entry.getValue());
    }

    if (excludeIdField != null && excludeIdValue != null) {
      query.setParameter("excludeId", excludeIdValue);
    }

    Number count = (Number) query.getSingleResult();
    return count.longValue() > 0;
  }

  public boolean checkReferenceExists(String tableName, Map<String, Object> filters, String excludeField, List<Object> excludeValues) {
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

  public Long getNextValFromDual(String sequenceName) {
    String sql = "SELECT " + sequenceName + ".NEXTVAL FROM DUAL";
    Query query = entityManager.createNativeQuery(sql);
    Number result = (Number) query.getSingleResult();
    return result != null ? result.longValue() : null;
  }
}
