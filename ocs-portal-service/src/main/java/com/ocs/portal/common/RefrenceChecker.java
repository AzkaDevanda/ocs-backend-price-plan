package com.ocs.portal.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Component
public class RefrenceChecker {
    @PersistenceContext
    private EntityManager entityManager;

    public void checkFieldNotReferenced(String tableName, String fieldName, Object value, String errorCode) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + fieldName + " = :value";
        Object count = entityManager.createNativeQuery(sql)
                .setParameter("value", value)
                .getSingleResult();
        if (((Number) count).intValue() > 0) {
            throw new IllegalStateException("Error " + errorCode + ": Field is referenced");
        }
    }
}
