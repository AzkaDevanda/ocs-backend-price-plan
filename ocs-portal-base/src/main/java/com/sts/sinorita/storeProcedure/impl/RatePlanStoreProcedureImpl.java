package com.sts.sinorita.storeProcedure.impl;

import com.sts.sinorita.storeProcedure.RatePlanStoreProcedure;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

@Repository
public class RatePlanStoreProcedureImpl implements RatePlanStoreProcedure {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void callDeleteRatePlanProcedure(Integer ratePlanId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PKG_PRICE_PLAN_CFG.P_DELETE_RATE_PLAN")
                .registerStoredProcedureParameter("IN_RATE_PLAN_ID", Integer.class, ParameterMode.IN)
                .setParameter("IN_RATE_PLAN_ID", ratePlanId);

        query.execute();
    }
}
