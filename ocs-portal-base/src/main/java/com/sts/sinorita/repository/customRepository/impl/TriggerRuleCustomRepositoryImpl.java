package com.sts.sinorita.repository.customRepository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sts.sinorita.dto.response.trigger.TriggerRuleResponseDto;
import com.sts.sinorita.repository.customRepository.TriggerRuleCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TriggerRuleCustomRepositoryImpl implements TriggerRuleCustomRepository {


    private static final ObjectMapper objectMapper = new ObjectMapper();
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TriggerRuleResponseDto> findTriggerRules(Integer offerVerId, String order_field, String order_direction, Character state, Integer spId) {

        StringBuilder sql = new StringBuilder("""
                SELECT\s
                                A.TRIGGER_ID,
                                A.SEQ,
                                TO_CHAR(A.EFF_DATE, 'yyyy-MM-DD'),
                                TO_CHAR(A.EXP_DATE, 'yyyy-MM-DD'),
                                A.STATE,
                                TO_CHAR(A.STATE_DATE, 'yyyy-MM-DD'),
                                A.SP_ID,
                                TO_CHAR(A.RULE_SCRIPT) AS RULE_SCRIPT,
                                TO_CHAR(A.SCRIPT_PAGE) AS SCRIPT_PAGE,
                                A.SCRIPT_TEMPLET_ID,
                                A.OFFER_VER_ID,
                                A.WORKFLOW_ID
                            FROM TRIGGER_RULE A
                            WHERE 1=1
                """);

        Map<String, Object> param = new HashMap<>();

        if (offerVerId != null) {
            sql.append(" AND A.OFFER_VER_ID = :offerVerId");
            param.put("offerVerId", offerVerId);
        }

        if (state != null) {
            sql.append(" AND A.STATE = :state");
            param.put("state", state);
        }

        if (spId != null) {
            sql.append("AND NVL(A.SP_ID = :spId");
            param.put("spId", spId);
        }

        if (order_field != null && order_direction != null) {
            Map<String, String> allowedSortFields = Map.of(
                    "eff_date", "A.EFF_DATE",
                    "exp_date", "A.EXP_DATE"
            );

            String column = allowedSortFields.get(order_field.toLowerCase());
            if (column != null) {
                sql.append(" ORDER BY ").append(column).append(" ").append(order_direction).append("\n");
            }
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        param.forEach(query::setParameter);

        // MAPPING TO DTO
        List<Object[]> resultList = query.getResultList();

        List<String> columns = List.of(
                "triggerId", "seq", "effDate", "expDate", "state",
                "stateDate", "spId", "ruleScript", "scriptPage",
                "scriptTempletId", "offerVerId", "workflowId"
        );


        return resultList.stream().map(row -> {
            Map<String, Object> rowMap = new HashMap<>();
            for (int i = 0; i < columns.size(); i++) {
                Object value = row[i];
                rowMap.put(columns.get(i), value);
            }
            return objectMapper.convertValue(rowMap, TriggerRuleResponseDto.class);
        }).toList();


    }
}
