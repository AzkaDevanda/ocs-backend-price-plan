package com.sts.sinorita.repository.customRepository.impl;

import com.sts.sinorita.entity.OfferVer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class OfferVerRepositoryImpl implements OfferVerRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    public boolean isOfferVerDateConflict(OfferVer dto) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.OFFER_VER_ID FROM OFFER_VER T WHERE T.OFFER_ID = :offerId ");

        Map<String, Object> params = new HashMap<>();
        params.put("offerId", dto.getOfferId());

        if (dto.getExpDate() == null) {
            sql.append(" AND (T.EXP_DATE > :effDate OR T.EXP_DATE IS NULL) ");
            params.put("effDate", dto.getEffDate());
        } else {
            sql.append("""
                AND (
                    (T.EFF_DATE < :eff1 AND T.EXP_DATE > :eff2) OR
                    (T.EFF_DATE < :exp1 AND T.EXP_DATE > :exp2) OR
                    (T.EFF_DATE > :eff3 AND T.EXP_DATE <= :exp3) OR
                    (T.EFF_DATE = :eff4) OR
                    (T.EFF_DATE < :eff5 AND T.EXP_DATE IS NULL)
                )
            """);

            params.put("eff1", dto.getEffDate());
            params.put("eff2", dto.getEffDate());
            params.put("exp1", dto.getExpDate());
            params.put("exp2", dto.getExpDate());
            params.put("eff3", dto.getEffDate());
            params.put("exp3", dto.getExpDate());
            params.put("eff4", dto.getEffDate());
            params.put("eff5", dto.getEffDate());
        }

        if ("A".equals(dto.getState())) {
            sql.append(" AND (T.STATE = 'A' OR T.STATE IS NULL) ");
        } else if ("G".equals(dto.getState())) {
            sql.append(" AND T.STATE = 'G' ");
        }

        if (dto.getId() != null) {
            sql.append(" AND T.OFFER_VER_ID <> :excludeId ");
            params.put("excludeId", dto.getId().toString());
        }

        Query nativeQuery = em.createNativeQuery(sql.toString());
        params.forEach(nativeQuery::setParameter);

        List<?> result = nativeQuery.getResultList();
        return result.isEmpty(); // TRUE = tidak konflik
    }

    @Override
    public long checkWithMaxVer(OfferVer dto) {
        StringBuilder sql = new StringBuilder();
        sql.append("""
            SELECT T.OFFER_VER_ID
            FROM OFFER_VER T
            WHERE T.OFFER_ID = :offerId
              AND T.EXP_DATE IS NULL
              AND T.EFF_DATE < :effDate
        """);

        if (StringUtils.hasText(dto.getState().toString())) {
            if ("A".equals(dto.getState())) {
                sql.append(" AND (T.STATE = 'A' OR T.STATE IS NULL) ");
            } else if ("G".equals(dto.getState())) {
                sql.append(" AND T.STATE = 'G' ");
            }
        }

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("offerId", dto.getOfferId());
        query.setParameter("effDate", dto.getEffDate());

        var result = query.getResultList();
        if (result == null || result.isEmpty()) {
            return 0L;
        }

        Object offerVerId = result.get(0);
        return ((Number) offerVerId).longValue();
    }
}
