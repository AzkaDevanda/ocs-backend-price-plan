package com.ocs.portal.repository.customRepository.impl;

import com.ocs.portal.repository.customRepository.OfferCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OfferCustomRepositoryImpl implements OfferCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Object[]> findAllWithDynamicFilter(Character applyLevel, Character type, String key, String value,
                                                   String sort, String sortBy, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        // Determine which table to join based on apply_level
        String pricePlanJoin = "";
        String pricePlanTypeJoin = "";
        String applyLevelCondition = "";

        // Gunakan parameter apply_level dinamis (misalnya dari filter)
        // Di sini, misal kita hardcode sementara, bisa juga dijadikan parameter di method

        if (applyLevel.equals('A')) {
            pricePlanJoin = "INNER JOIN ACCT_PRICE_PLAN app ON app.PRICE_PLAN_ID = pp.PRICE_PLAN_ID";
            pricePlanTypeJoin = "INNER JOIN PRICE_PLAN_TYPE ppt ON ppt.PRICE_PLAN_TYPE = app.PRICE_PLAN_TYPE";
            applyLevelCondition = "pp.apply_level = 'A'";
        } else if (applyLevel.equals('S')) {
            pricePlanJoin = "INNER JOIN SUBS_PRICE_PLAN spp ON spp.PRICE_PLAN_ID = pp.PRICE_PLAN_ID";
            pricePlanTypeJoin = "INNER JOIN PRICE_PLAN_TYPE ppt ON ppt.PRICE_PLAN_TYPE = spp.PRICE_PLAN_TYPE";
            applyLevelCondition = "pp.apply_level = 'S'";
        }

        // Construct SQL SELECT
        sql.append("""
                            SELECT
                                offer.OFFER_ID,
                                offer.offer_name,
                                offer.offer_code,
                                ppt.price_plan_type_name,
                                offer.eff_date,
                                offer.exp_date,
                                pp.APPLY_LEVEL
                            FROM OFFER offer
                            INNER JOIN PRICE_PLAN pp ON pp.PRICE_PLAN_ID = offer.OFFER_ID
                        """).append(pricePlanJoin).append("\n")
                .append(pricePlanTypeJoin).append("\n")
                .append("WHERE ").append(applyLevelCondition).append("\n")
                .append("  AND offer.state = 'A'\n")
                .append("  AND offer.offer_type = '4'\n");

        // Construct SQL COUNT
        countSql.append("""
                            SELECT COUNT(*)
                            FROM OFFER offer
                            INNER JOIN PRICE_PLAN pp ON pp.PRICE_PLAN_ID = offer.OFFER_ID
                        """).append(pricePlanJoin).append("\n")
                .append(pricePlanTypeJoin).append("\n")
                .append("WHERE ").append(applyLevelCondition).append("\n")
                .append("  AND offer.state = 'A'\n")
                .append("  AND offer.offer_type = '4'\n");

        // Tambahan filter: price plan type
        if (type != null) {
            sql.append(" AND ppt.PRICE_PLAN_TYPE = :type\n");
            countSql.append(" AND ppt.PRICE_PLAN_TYPE = :type\n");
            parameters.put("type", type);
        }

        // Tambahan dynamic LIKE filter
        if (key != null && value != null) {
            Map<String, String> allowedKeys = Map.of(
                    "offer_name", "offer.offer_name",
                    "offer_code", "offer.offer_code",
                    "price_plan_type_name", "ppt.price_plan_type_name",
                    "created_date", "offer.created_date"
            );

            String column = allowedKeys.get(key.toLowerCase());
            if (column != null) {
                sql.append(" AND LOWER(").append(column).append(") LIKE :value\n");
                countSql.append(" AND LOWER(").append(column).append(") LIKE :value\n");
                parameters.put("value", "%" + value.toLowerCase() + "%");
            }
        }

        // Sorting
        if (sort != null && sortBy != null) {
            Map<String, String> allowedSortFields = Map.of(
                    "created_date", "offer.created_date",
                    "offer_name", "offer.offer_name",
                    "offer_code", "offer.offer_code",
                    "price_plan_type_name", "ppt.price_plan_type_name",
                    "eff_date", "offer.eff_date",
                    "exp_date", "offer.exp_date"
            );

            String column = allowedSortFields.get(sort.toLowerCase());
            if (column != null) {
                sql.append(" ORDER BY ").append(column).append(" ").append(sortBy).append("\n");
            }
        }

        // Query data
        Query dataQuery = entityManager.createNativeQuery(sql.toString());
        parameters.forEach(dataQuery::setParameter);
        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());

        // Query count
        Query countQuery = entityManager.createNativeQuery(countSql.toString());
        parameters.forEach(countQuery::setParameter);
        BigDecimal total = (BigDecimal) countQuery.getSingleResult();

        List<Object[]> results = dataQuery.getResultList();
        return new PageImpl<>(results, pageable, total.longValue());
    }

    @Override
    public Optional<Object[]> findOfferDetailByPricePlanIdAndApplyLevel(Integer pricePlanId, Character applyLevel) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        String pricePlanJoin = "";
        String pricePlanTypeJoin = "";
        String applyLevelCondition = "";

        if (applyLevel.equals('A')) {
            pricePlanJoin = "INNER JOIN ACCT_PRICE_PLAN app ON app.PRICE_PLAN_ID = pp.PRICE_PLAN_ID";
            pricePlanTypeJoin = "INNER JOIN PRICE_PLAN_TYPE ppt ON ppt.PRICE_PLAN_TYPE = app.PRICE_PLAN_TYPE";
            applyLevelCondition = "pp.APPLY_LEVEL = 'A'";
        } else if (applyLevel.equals('S')) {
            pricePlanJoin = "INNER JOIN SUBS_PRICE_PLAN spp ON spp.PRICE_PLAN_ID = pp.PRICE_PLAN_ID";
            pricePlanTypeJoin = "INNER JOIN PRICE_PLAN_TYPE ppt ON ppt.PRICE_PLAN_TYPE = spp.PRICE_PLAN_TYPE";
            applyLevelCondition = "pp.APPLY_LEVEL = 'S'";
        }

        sql.append("""
                        SELECT
                            o.OFFER_ID,
                            o.OFFER_NAME,
                            o.OFFER_CODE,
                            ppt.PRICE_PLAN_TYPE,
                            o.COMMENTS,
                            o.EFF_DATE,
                            o.EXP_DATE,
                            pp.PRICE_PLAN_ID,
                            pp.APPLY_LEVEL,
                            pp.SERV_TYPE,
                            ast.SERV_TYPE_NAME,
                            nt.NETWORK_TYPE,
                            nt.NETWORK_TYPE_NAME
                        FROM OFFER o
                        INNER JOIN PRICE_PLAN pp ON pp.PRICE_PLAN_ID = o.OFFER_ID
                        LEFT JOIN ALL_SERV_TYPE ast ON pp.SERV_TYPE = ast.SERV_TYPE
                        LEFT JOIN NETWORK_TYPE nt ON ast.NETWORK_TYPE = nt.NETWORK_TYPE
                        """).append(pricePlanJoin).append("\n")
                .append(pricePlanTypeJoin).append("\n")
                .append("WHERE o.OFFER_ID = :pricePlanId AND o.STATE = 'A' AND ")
                .append(applyLevelCondition);

        parameters.put("pricePlanId", pricePlanId);

        Query query = entityManager.createNativeQuery(sql.toString());
        parameters.forEach(query::setParameter);

        List<Object[]> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(resultList.get(0));
    }

}
