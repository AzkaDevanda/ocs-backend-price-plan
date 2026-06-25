package com.ocs.portal.storeProcedure.impl;

import com.ocs.portal.storeProcedure.PriceplanStoreProcedure;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

@Repository
public class PriceplanStoreProcedureImpl implements PriceplanStoreProcedure {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void callCopyPricePlanVer(Long srcId, Long destId, String prefix, String postfix) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("PKG_PRICE_PLAN_CFG.P_COPY_PRICE_PLAN_VER");

        query.registerStoredProcedureParameter("IN_SRC_PRICE_PLAN_VER_ID", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("IN_DEST_PRICE_PLAN_VER_ID", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("IN_PREFIX", String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("IN_POSTFIX", String.class, jakarta.persistence.ParameterMode.IN);

        query.setParameter("IN_SRC_PRICE_PLAN_VER_ID", srcId);
        query.setParameter("IN_DEST_PRICE_PLAN_VER_ID", destId);
        query.setParameter("IN_PREFIX", prefix);
        query.setParameter("IN_POSTFIX", postfix);

        query.execute();
    }
}
