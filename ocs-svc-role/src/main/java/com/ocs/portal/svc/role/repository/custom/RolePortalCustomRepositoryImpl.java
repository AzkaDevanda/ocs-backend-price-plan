package com.ocs.portal.svc.role.repository.custom;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("rolePortalCustomRepository")
public class RolePortalCustomRepositoryImpl implements RolePortalCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public int fieldIsReferenced(String tableName, Long roleId) {
        // Validasi nama tabel agar aman dari SQL injection
        List<String> allowedTables = List.of("POT.BFM_JOB_ROLE","POT.BFM_ROLE_DATA_PRIV","POT.BFM_ROLE_PORTAL", "POT.BFM_ROLE_PRIV", "POT.BFM_USER_ROLE");
        if (!allowedTables.contains(tableName.toUpperCase())) {
            throw new IllegalArgumentException("Invalid table name");
        }

        // Buat SQL dengan WHERE ROLE_ID = :roleId
        String sql = "SELECT COUNT(1) FROM " + tableName + " WHERE ROLE_ID = :roleId";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("roleId", roleId);

        Object result = query.getSingleResult();
        return ((Number) result).intValue();
    }



}
