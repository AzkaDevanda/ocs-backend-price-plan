package com.sts.sinorita.repository;

import com.sts.sinorita.dto.request.priceplan.PriceParamDto;
import com.sts.sinorita.entity.PriceParam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PriceParamRepository extends JpaRepository<PriceParam, Long> {
    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM PRICE_PARAM
        WHERE PRICE_ID = :priceId
          AND (SIMPLE_PARAM_ID = :paramId
               OR TABLE_PARAM_ID  = :paramId)
        """, nativeQuery = true)
    int delPriceParamByPriceIdAndParamId(
            @Param("priceId") Long priceId,
            @Param("paramId") Long paramId);


    @Query(value = """
        SELECT
            PRICE_PARAM_ID   AS priceParamId,
            PRICE_ID         AS priceId,
            PARAM_TYPE       AS paramType,
            SIMPLE_PARAM_ID  AS simpleParamId,
            TABLE_PARAM_ID   AS tableParamId,
            SP_ID            AS spId
        FROM PRICE_PARAM
        WHERE 1=1
          AND (:priceId IS NULL OR PRICE_ID = :priceId)
          AND (
                :paramId IS NULL
                OR SIMPLE_PARAM_ID = :paramId
                OR TABLE_PARAM_ID  = :paramId
          )
        """, nativeQuery = true)
    PriceParamDto[] qryPriceParam(
            @Param("priceId") Long priceId,
            @Param("paramId") Long paramId);


}
