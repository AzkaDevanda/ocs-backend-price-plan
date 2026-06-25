package com.ocs.portal.storeProcedure.impl;

import com.ocs.portal.storeProcedure.PriceVerStoreProcedure;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

@Repository
public class PriceVerStoreProcedureImpl implements PriceVerStoreProcedure {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void callDeletePriceVerProcedure(Integer priceVerId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PKG_PRICE_PLAN_CFG.P_DELETE_PRICE_VER")
                .registerStoredProcedureParameter("IN_PRICE_VER_ID", Integer.class, ParameterMode.IN)
                .setParameter("IN_PRICE_VER_ID", priceVerId);

        query.execute();
    }
}
