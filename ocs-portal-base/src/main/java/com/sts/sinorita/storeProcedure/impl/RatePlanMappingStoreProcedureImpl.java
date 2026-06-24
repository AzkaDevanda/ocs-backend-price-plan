package com.sts.sinorita.storeProcedure.impl;

import com.sts.sinorita.storeProcedure.RatePlanMappingStoreProcedure;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

@Repository
public class RatePlanMappingStoreProcedureImpl implements RatePlanMappingStoreProcedure {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void callRatePlanMappingStoreProcedure(Integer ratePlanId, Integer reId, Integer offerVerId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PKG_PRICE_PLAN_CFG.P_DELETE_RATE_PLAN_MAPPING")
                .registerStoredProcedureParameter("IN_RATE_PLAN_ID", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("IN_RE_ID", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("IN_OFFER_VER_ID", Integer.class, ParameterMode.IN)
                .setParameter("IN_RATE_PLAN_ID", ratePlanId)
                .setParameter("IN_RE_ID", reId)
                .setParameter("IN_OFFER_VER_ID", offerVerId);

        query.execute();
    }
}
