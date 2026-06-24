package com.sts.sinorita.repository;

import com.sts.sinorita.projection.pricePlan.rateplan.QryRatePlanProjection;
import com.sts.sinorita.storeProcedure.RatePlanStoreProcedure;
import com.sts.sinorita.entity.RatePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatePlanRepository extends JpaRepository<RatePlan, Integer>, RatePlanStoreProcedure {

    @Query("SELECT COUNT(r) FROM RatePlan r WHERE r.reId = :reId AND r.offerVerId = :offerVerId")
    Long countByReIdAndOfferVerId(@Param("reId") Integer reId, @Param("offerVerId") Integer offerVerId);

    @Query(value = """
            SELECT
            	A.id AS ratePlanId,
            	A.ratePlanName AS ratePlanName,
            	C.priority AS priority,
            	A.ratePlanType AS ratePlanType,
            	A.ratePlanCode AS ratePlanCode,
            	A.mappingExit AS mappingExit,
            	CASE
            		WHEN NOT EXISTS (
            		SELECT
            			B.id
            		FROM
            			RatePlanZone B
            		WHERE
            			A.id = B.ratePlanId) THEN
                      '1'
            		ELSE
                      '2'
            	END AS ratePlanMapping,
            	C.id.offerVerId AS offerVerId,
            	C.id.reId AS reId,
            	A.templateFlag AS templateFlag,
            	A.remarks AS remarks
            FROM
            	RatePlan A,
            	RatePlanMapping C
            WHERE
            	A.id = C.id.ratePlanId
            	AND C.id.offerVerId = :offerVerId
            	AND C.id.reId = :reId
            	AND (:spId IS NULL OR NVL(A.spId, 0)= :spId)
              AND (:ratePlanName IS NULL OR LOWER(A.ratePlanName) LIKE LOWER(CONCAT('%', :ratePlanName, '%')))
            ORDER BY
            	C.priority
            """)
    List<QryRatePlanProjection> findRatePlanByOfferId(@Param("offerVerId") Integer offerVerId, @Param("reId") Integer reId,
                                                      @Param("spId") Integer spId,
                                                      @Param("ratePlanName") String ratePlanName );

    @Query(value = "SELECT COUNT(*) FROM RATE_PLAN rp " +
            "JOIN OFFER_VER ov ON rp.OFFER_VER_ID = ov.OFFER_VER_ID " +
            "JOIN OFFER o ON ov.OFFER_ID = o.OFFER_ID " +
            "LEFT JOIN PRICE_VER pv ON rp.RATE_PLAN_ID = pv.RATE_PLAN_ID " +
            "WHERE ov.OFFER_VER_ID = :offerVerId AND rp.RE_ID = :reId", nativeQuery = true)
    int countRatePlanByOfferVerIdAndReId(@Param("offerVerId") Integer offerId, @Param("reId") Integer reId);

    @Query(value = "SELECT rp.* FROM RATE_PLAN rp " +
            "JOIN OFFER_VER ov ON rp.OFFER_VER_ID = ov.OFFER_VER_ID " +
            "JOIN OFFER o ON ov.OFFER_ID = o.OFFER_ID " +
            "LEFT JOIN PRICE_VER pv ON rp.RATE_PLAN_ID = pv.RATE_PLAN_ID " +
            "WHERE ov.OFFER_VER_ID = :offerVerId", nativeQuery = true)
    RatePlan getRatePlanByOfferVerId(@Param("offerVerId") Integer offerVerId);

    @Modifying
    @Query(value = "DELETE FROM RATE_PLAN rp WHERE rp.OFFER_VER_ID = :offerVerId AND rp.RE_ID = :reId", nativeQuery = true)
    void deleteRatePlanByOfferVerIdAndReId(@Param("offerVerId") Integer offerVerId, @Param("reId") Integer reId);

    // @Modifying
    // @Query("DELETE FROM RatePlan rp WHERE rp.id = :priceVerId")
    // void clearSrcRatePlanRefByPriceVerId(@Param("priceVerId") Integer
    // priceVerId);

}
