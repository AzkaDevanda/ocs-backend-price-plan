package com.sts.sinorita.repository;

import com.sts.sinorita.dto.request.priceplan.PriceParamDto;
import com.sts.sinorita.dto.request.priceplan.PricePlanParamDto;
import com.sts.sinorita.entity.PricePlanParam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PricePlanParamRepository extends JpaRepository<PricePlanParam,Integer> {
    @Query(value = """
        SELECT
            PRICE_PLAN_PARAM_ID AS pricePlanParamId,
            OFFER_VER_ID        AS offerVerId,
            PARAM_TYPE          AS paramType,
            SIMPLE_PARAM_ID     AS simpleParamId,
            TABLE_PARAM_ID      AS tableParamId,
            INSIDE_FLAG         AS insideFlag,
            SP_ID               AS spId
        FROM PRICE_PLAN_PARAM
        WHERE 1 = 1
          AND (:offerVerId IS NULL OR OFFER_VER_ID = :offerVerId)
          AND (
                :paramId IS NULL
                OR SIMPLE_PARAM_ID = :paramId
                OR TABLE_PARAM_ID = :paramId
          )
        """, nativeQuery = true)
    PricePlanParamDto[] qryPricePlanParam(
            @Param("offerVerId") Long offerVerId,
            @Param("paramId") Long paramId
    );

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM PRICE_PLAN_PARAM
        WHERE OFFER_VER_ID = :offerVerId
          AND (SIMPLE_PARAM_ID = :paramId
                OR TABLE_PARAM_ID  = :paramId)
        """, nativeQuery = true)
    int delByOfferVerIdAndParamId(
            @Param("offerVerId") Long offerVerId,
            @Param("paramId") Long paramId
    );
}
