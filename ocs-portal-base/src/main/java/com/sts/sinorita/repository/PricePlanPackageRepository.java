package com.sts.sinorita.repository;

import com.sts.sinorita.entity.PricePlanPackage;
import com.sts.sinorita.projection.PricePlanRelationProjection;
import com.sts.sinorita.projection.pricePlan.priceplanpackage.PricePlanJoinPackageProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricePlanPackageRepository extends JpaRepository<PricePlanPackage, Integer> {

    @Query(value = """
            SELECT B.PRICE_PLAN_ID pricePlanId,
            C.OFFER_ID offerId,
            C.OFFER_NAME offerName,
            NVL(A.DEFAULT_FLAG,'N') DEFAULT_FLAG
            FROM PRICE_PLAN_PACKAGE A, PRICE_PLAN B, OFFER C
            WHERE A.PRICE_PLAN_PACKAGE_ID = B.PRICE_PLAN_ID
            AND B.PRICE_PLAN_ID = C.OFFER_ID
            AND B.IS_PACKAGE = 'Y'
            AND C.STATE = 'A'
            AND A.MEM_PRICE_PLAN_ID = :PRICE_PLAN_ID
            AND NVL(A.SP_ID,0)= :SP_ID
            """, nativeQuery = true)
    Page<PricePlanJoinPackageProjection> qryPricePlanJoinPackage(@Param("PRICE_PLAN_ID") Integer pricePlanId, @Param("SP_ID") Integer spId, Pageable pageable);

    @Query(value = """
        SELECT B.PRICE_PLAN_ID AS pricePlanId,
               C.OFFER_ID AS offerId,
               C.OFFER_NAME AS offerName,
               NVL(A.DEFAULT_FLAG, 'N') AS defaultFlag
        FROM PRICE_PLAN_PACKAGE A
        JOIN PRICE_PLAN B ON A.PRICE_PLAN_PACKAGE_ID = B.PRICE_PLAN_ID
        JOIN OFFER C ON B.PRICE_PLAN_ID = C.OFFER_ID
        WHERE B.IS_PACKAGE = 'Y'
          AND C.STATE = 'A'
          AND A.MEM_PRICE_PLAN_ID = :memPricePlanId
          AND NVL(A.SP_ID, 0) = '0'
        ORDER BY C.OFFER_NAME
        """, nativeQuery = true)
    List<PricePlanRelationProjection> checkPricePlanRelations(@Param("memPricePlanId") Integer memPricePlanId);

}
