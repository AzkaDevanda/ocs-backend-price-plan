package com.ocs.portal.svc.role.repository.custom;

import com.ocs.portal.svc.role.dto.response.StaffDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("bfmStaffCustomRepository")
public class BfmStaffCustomRepositoryImpl implements BfmStaffCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, Object> getCurrentValueInAttrTable(
            String tableName, String keyName, Object keyValue, String attrCode) {

        String sql = String.format("""
                    SELECT ATTR_CODE, ATTR_VALUE 
                    FROM %s 
                    WHERE %s = :keyValue 
                      AND ATTR_CODE = :attrCode
                """, tableName, keyName);

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("keyValue", keyValue);
        query.setParameter("attrCode", attrCode);

        Object[] result = (Object[]) query.getSingleResult();

        Map<String, Object> map = new HashMap<>();
        map.put("ATTR_CODE", result[0]);
        map.put("ATTR_VALUE", result[1]);

        return map;
    }

    @Override
    public List<Map<String, Object>> getCurrentValueInAttrTableList(
            String tableName, String keyName, Object keyValue, String attrCode) {

        // ⚠️ Amankan tableName dan keyName jika ini input dari user
        String sql = String.format("""
                    SELECT ATTR_CODE, ATTR_VALUE 
                    FROM %s 
                    WHERE %s = :keyValue 
                      AND ATTR_CODE = :attrCode
                """, tableName, keyName);

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("keyValue", keyValue);
        query.setParameter("attrCode", attrCode);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mapList = new ArrayList<>();

        for (Object[] row : resultList) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put("ATTR_CODE", row[0]);
            rowMap.put("ATTR_VALUE", row[1]);
            mapList.add(rowMap);
        }

        return mapList;
    }

    @Modifying
    @Override
    public void updateColValue(String tableName, String colName, Object colValue, String keyName, Object keyValue) {
        // Validasi input agar aman (penting untuk mencegah SQL Injection!)
        if (!isSafeIdentifier(tableName) || !isSafeIdentifier(colName) || !isSafeIdentifier(keyName)) {
            throw new IllegalArgumentException("Invalid table or column name");
        }

        String sql;
        if (colValue == null) {
            sql = String.format(
                    "UPDATE %s SET %s = NULL WHERE %s = :keyValue",
                    tableName, colName, keyName
            );
        } else {
            sql = String.format(
                    "UPDATE %s SET %s = :colValue WHERE %s = :keyValue",
                    tableName, colName, keyName
            );
        }

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("keyValue", keyValue);
        if (colValue != null) {
            query.setParameter("colValue", colValue);
        }

        query.executeUpdate();
    }

    // Utility method untuk memvalidasi nama table/kolom
    private boolean isSafeIdentifier(String name) {
        return name != null && name.matches("[a-zA-Z0-9_]+");
    }


    @Modifying
    @Override
    public void updateStaff(StaffDto dto) {
        StringBuilder sql = new StringBuilder("UPDATE BFM_STAFF SET ");
        Map<String, Object> params = new HashMap<>();

        if (dto.getStaffCode() != null) {
            sql.append("STAFF_CODE = :staffCode, ");
            params.put("staffCode", dto.getStaffCode());
        }
        if (dto.getAddress() != null) {
            sql.append("ADDRESS = :address, ");
            params.put("address", dto.getAddress());
        }
        if (dto.getEmail() != null) {
            sql.append("EMAIL = :email, ");
            params.put("email", dto.getEmail());
        }
        if (dto.getPhone() != null) {
            sql.append("PHONE = :phone, ");
            params.put("phone", dto.getPhone());
        }
        if (dto.getStaffName() != null) {
            sql.append("STAFF_NAME = :staffName, ");
            params.put("staffName", dto.getStaffName());
        }
        if (dto.getExtStr07() != null && !dto.getExtStr07().isEmpty()) {
            sql.append("EXT_STR_07 = :extStr07, ");
            params.put("extStr07", dto.getExtStr07());
        }
        if (dto.getState() != null) {
            sql.append("STATE = :state, ");
            params.put("state", dto.getState());
        }
        if (dto.getStateDate() != null) {
            sql.append("STATE_DATE = :stateDate, ");
            params.put("stateDate", dto.getStateDate());
        }

        // UPDATE_DATE always updated
        sql.append("UPDATE_DATE = :updateDate ");
        params.put("updateDate", dto.getUpdateDate());

        sql.append("WHERE STAFF_ID = :staffId");
        params.put("staffId", dto.getStaffId());

        Query query = entityManager.createNativeQuery(sql.toString());

        // Set parameters
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        query.executeUpdate();
    }


    @Modifying
    @Override
    public void deleteAttrExtValue(String tableName, String keyName, Object keyValue, String attrCode) {
        // Validate to avoid SQL injection
        if (!isSafeIdentifier(tableName) || !isSafeIdentifier(keyName)) {
            throw new IllegalArgumentException("Invalid table or column name");
        }

        StringBuilder sql = new StringBuilder("DELETE FROM " + tableName + " WHERE " + keyName + " = :keyValue");

        if (attrCode != null) {
            sql.append(" AND ATTR_CODE = :attrCode");
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("keyValue", keyValue);
        if (attrCode != null) {
            query.setParameter("attrCode", attrCode);
        }

        query.executeUpdate();
    }

    @Modifying
    @Override
    public void insertAttrExtValue(String tableName, String keyName, Object keyValue,
                                   String attrCode, String attrName, String attrValue) {

        // Validasi untuk menghindari SQL injection
        if (!isSafeIdentifier(tableName) || !isSafeIdentifier(keyName)) {
            throw new IllegalArgumentException("Invalid table or column name");
        }

        String sql = String.format("""
                    INSERT INTO %s (%s, ATTR_CODE, ATTR_NAME, ATTR_VALUE)
                    VALUES (:keyValue, :attrCode, :attrName, :attrValue)
                """, tableName, keyName);

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("keyValue", keyValue);
        query.setParameter("attrCode", attrCode);
        query.setParameter("attrName", attrName);
        query.setParameter("attrValue", attrValue);

        query.executeUpdate();
    }

    @Modifying
    @Override
    public void updateAttrExtValue(String tableName, String keyName, Object keyValue,
                                   String attrCode, String attrValue) {

        if (!isSafeIdentifier(tableName) || !isSafeIdentifier(keyName)) {
            throw new IllegalArgumentException("Invalid table or column name");
        }

        String sql = String.format("""
                    UPDATE %s
                    SET ATTR_VALUE = :attrValue
                    WHERE %s = :keyValue
                      AND ATTR_CODE = :attrCode
                """, tableName, keyName);

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("attrValue", attrValue);
        query.setParameter("keyValue", keyValue);
        query.setParameter("attrCode", attrCode);

        query.executeUpdate();
    }

}
