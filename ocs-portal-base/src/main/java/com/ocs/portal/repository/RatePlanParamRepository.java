package com.ocs.portal.repository;

import com.ocs.portal.dto.request.priceplan.RatePlanParamDto;
import com.ocs.portal.entity.RatePlanParam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatePlanParamRepository extends JpaRepository<RatePlanParam, Long> {

    @Query(value = """
        SELECT
            RATE_PLAN_PARAM_ID AS ratePlanParamId,
            RATE_PLAN_ID       AS ratePlanId,
            PARAM_TYPE         AS paramType,
            SIMPLE_PARAM_ID    AS simpleParamId,
            TABLE_PARAM_ID     AS tableParamId,
            SP_ID              AS spId
        FROM RATE_PLAN_PARAM
        WHERE 1=1
          AND (:ratePlanId IS NULL OR RATE_PLAN_ID = :ratePlanId)
          AND (
                :paramId IS NULL
                OR SIMPLE_PARAM_ID = :paramId
                OR TABLE_PARAM_ID  = :paramId
          )
        """, nativeQuery = true)
    RatePlanParamDto[] qryRatePlanParam(
            @Param("ratePlanId") Long ratePlanId,
            @Param("paramId") Long paramId);


    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM RATE_PLAN_PARAM
        WHERE RATE_PLAN_ID = :ratePlanId
          AND (SIMPLE_PARAM_ID = :paramId
               OR TABLE_PARAM_ID = :paramId)
        """, nativeQuery = true)
    int delRatePlanParamByRatePlanIdAndParamId(
            @Param("ratePlanId") Long ratePlanId,
            @Param("paramId") Long paramId);
}
