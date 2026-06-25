package com.ocs.portal.storeProcedure.impl;

import com.ocs.portal.storeProcedure.RePricePlanStoreProcedure;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

@Repository
public class RePricePlanStoreProcedureImpl implements RePricePlanStoreProcedure {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void callDeleteRePricePlanProcedure(Integer reId, Integer offerVerId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PKG_PRICE_PLAN_CFG.P_DELETE_RE_PRICE_PLAN")
                .registerStoredProcedureParameter("IN_RE_ID", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("IN_PRICE_PLAN_VER_ID", Integer.class, ParameterMode.IN)
                .setParameter("IN_RE_ID", reId)
                .setParameter("IN_PRICE_PLAN_VER_ID", offerVerId);

        query.execute();
    }
}
