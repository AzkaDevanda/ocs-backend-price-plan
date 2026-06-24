package com.sts.sinorita.storeProcedure.impl;

import com.sts.sinorita.storeProcedure.MappingStoreProcedure;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

@Repository
public class MappingStoreProcedureImpl implements MappingStoreProcedure {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void callDeleteMappingProcedure(Integer mappingId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PKG_PRICE_PLAN_CFG.P_DELETE_MAPPING")
                .registerStoredProcedureParameter("IN_MAPPING_ID", Integer.class, ParameterMode.IN)
                .setParameter("IN_MAPPING_ID", mappingId);

        query.execute();
    }
}
