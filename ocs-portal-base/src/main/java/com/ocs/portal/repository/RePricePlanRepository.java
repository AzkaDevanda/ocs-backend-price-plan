package com.ocs.portal.repository;

import com.ocs.portal.projection.pricePlan.rateplan.ModRePricePlanProjection;
import com.ocs.portal.projection.pricePlan.rateplan.QryRePricePlanByReIdAndOfferVerIdProjection;
import com.ocs.portal.storeProcedure.RePricePlanStoreProcedure;
import com.ocs.portal.entity.RePricePlan;
import com.ocs.portal.entity.RePricePlanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RePricePlanRepository extends JpaRepository<RePricePlan, RePricePlanId>, RePricePlanStoreProcedure {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM RePricePlan r WHERE r.id.reId = :reId AND r.id.offerVerId = :offerVerId")
    boolean existsByReIdAndOfferVerId(Integer reId, Integer offerVerId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM RePricePlan r WHERE r.id.offerVerId = :offerVerId")
    boolean existsByOfferVerId(Integer offerVerId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN 1 ELSE 0 END " +
            "FROM RePricePlan r " +
            "JOIN RatePlan rp ON r.id.reId = rp.reId " +
            "JOIN PriceVer pv ON rp.id = pv.ratePlanId " +
            "WHERE pv.id = :priceVerId")
    Optional<Integer> existByOfferVerId(@Param("priceVerId") Integer priceVerId);

    @Query(value = """
        SELECT
            T.RE_ID               AS reId,
            T.OFFER_VER_ID        AS offerVerId,
            NVL(T.SP_ID,0)        AS spId,
            T.RULE_SCRIPT         AS ruleScript,
            T.SCRIPT_PAGE         AS scriptPage,
            T.SCRIPT_TEMPLET_ID   AS scriptTempletId,
            T.WORKFLOW_ID         AS workflowId
        FROM RE_PRICE_PLAN T
        WHERE T.RE_ID = :reId
          AND T.OFFER_VER_ID = :offerVerId
          AND NVL(T.SP_ID,0) = :spId
        """, nativeQuery = true)
    List<QryRePricePlanByReIdAndOfferVerIdProjection> qryRePricePlanByReIdAndOfferVerId(
            @Param("reId") Long reId,
            @Param("offerVerId") Long offerVerId,
            @Param("spId") Long spId);

    @Query(value = """
        SELECT
            T.RE_ID        AS reId,
            T.OFFER_VER_ID AS offerVerId,
            T.SP_ID        AS spId
        FROM RE_PRICE_PLAN T
        WHERE T.RE_ID = :reId
          AND T.OFFER_VER_ID = :offerVerId
        """, nativeQuery = true)
    Optional<ModRePricePlanProjection> findByReIdAndOfferVerId(
            @Param("reId") Integer reId,
            @Param("offerVerId") Integer offerVerId
    );


    Optional<RePricePlan> findById_ReIdAndId_OfferVerId(
            Integer reId,
            Integer offerVerId
    );

    @Modifying
    @Query(
            value = """
            UPDATE RE_PRICE_PLAN
            SET SCRIPT_PAGE = :scriptPage
            WHERE RE_ID = :reId
              AND OFFER_VER_ID = :offerVerId
        """,
            nativeQuery = true)
    int updateScriptPage(
            @Param("scriptPage") byte[] scriptPage,
            @Param("reId") Integer reId,
            @Param("offerVerId") Integer offerVerId
    );
}
