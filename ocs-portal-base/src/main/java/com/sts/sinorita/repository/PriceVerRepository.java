package com.sts.sinorita.repository;


import com.sts.sinorita.projection.pricePlan.price.QryPriceVerProjection;
import com.sts.sinorita.storeProcedure.PriceVerStoreProcedure;
import com.sts.sinorita.entity.PriceVer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceVerRepository extends JpaRepository<PriceVer, Integer>, PriceVerStoreProcedure {

    @Query(value = "SELECT * FROM PRICE_VER pv\n" +
            "WHERE pv.RATE_PLAN_ID = :ratePlanId\n" +
            "ORDER BY pv.EFF_DATE DESC\n" +
            "FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    PriceVer getExpDatePriceVerByRatePlanId(@Param("ratePlanId") Integer ratePlanId);

    @Query(value = "SELECT\n" +
            "    CASE \n" +
            "        WHEN (\n" +
            "            SELECT COUNT(*) \n" +
            "            FROM (\n" +
            "                SELECT pv.EXP_DATE \n" +
            "                FROM PRICE_VER pv \n" +
            "                WHERE pv.RATE_PLAN_ID = :ratePlanId \n" +
            "                ORDER BY pv.EFF_DATE DESC \n" +
            "                FETCH FIRST 1 ROWS ONLY\n" +
            "            ) sub \n" +
            "            WHERE sub.EXP_DATE IS NULL\n" +
            "        ) = 1 THEN 1\n" +
            "        ELSE 0\n" +
            "    END\n" +
            "FROM DUAL\n",
            nativeQuery = true)
    Integer existsExpDateIsNullRaw(@Param("ratePlanId") Integer ratePlanId);

    @Query("SELECT COUNT(p) FROM PriceVer p WHERE p.ratePlanId = :ratePlanId")
    int countByRatePlanId(@Param("ratePlanId") Integer ratePlanId);

    @Query("SELECT p FROM PriceVer p WHERE p.ratePlanId = :ratePlanId")
    List<PriceVer> findByRatePlanId(@Param("ratePlanId") Integer ratePlanId);

    @Modifying
    @Query("DELETE FROM PriceVer p WHERE p.ratePlanId = :ratePlanId")
    void deleteByRatePlanId(@Param("ratePlanId") Integer ratePlanId);

    @Query(value = "SELECT pv.* FROM price_ver pv " +
            "JOIN rate_plan rp ON pv.rate_plan_id = rp.rate_plan_id " +
            "JOIN re_price_plan rpp ON rp.offer_ver_id = rpp.offer_ver_id " +
            "JOIN offer_ver ov ON rpp.offer_ver_id = ov.offer_ver_id " +
            "WHERE ov.offer_ver_id = :offerVerId", nativeQuery = true)
    PriceVer findByOfferVerId(@Param("offerVerId") Integer offerVerId);

    @Query(value = "SELECT pv FROM PriceVer pv WHERE pv.id = :priceVerId")
    Optional<PriceVer> selectVerById(@Param("priceVerId") Integer priceVerId);

    @Query(value = """
            SELECT
            	T.id as priceVerId,
            	T.ratePlanId as ratePlanId,
            	T.mappingId as mappingId,
            	T.effDate as effDate,
            	T.expDate as expDate
            FROM
            	PriceVer T
            WHERE
            	1 = 1
            	AND (:PRICE_VER_ID IS NULL OR T.id = :PRICE_VER_ID)
            	AND (:RATE_PLAN_ID IS NULL OR T.ratePlanId = :RATE_PLAN_ID)
            	AND (:MAPPING_ID IS NULL OR T.mappingId = :MAPPING_ID)
            	AND (:SP_ID IS NULL OR COALESCE(T.spId, 0)=:SP_ID)
            ORDER BY
            	T.effDate,
            	T.id
            """)
    List<QryPriceVerProjection> qryPriceVer(@Param("PRICE_VER_ID") Integer priceVerId,
                                            @Param("RATE_PLAN_ID") Integer ratePlanId,
                                            @Param("MAPPING_ID") Integer mappingId,
                                            @Param("SP_ID") Integer spId);

    // Jika EXP_DATE null
    @Query("""
        SELECT p FROM PriceVer p
        WHERE (p.expDate IS NULL OR p.expDate > :effDate)
          AND p.ratePlanId = :ratePlanId
          AND (:mappingId IS NULL OR p.mappingId = :mappingId)
          AND p.id <> :priceVerId
    """)
    List<PriceVer> findConflictsWithoutExpDate(
            @Param("effDate") LocalDate effDate,
            @Param("ratePlanId") Integer ratePlanId,
            @Param("mappingId") Integer mappingId,
            @Param("priceVerId") Integer priceVerId
    );

    // Jika EXP_DATE tidak null
    @Query("""
    SELECT p FROM PriceVer p
    WHERE (
          (:effDate > p.effDate AND :effDate < COALESCE(p.expDate, :defaultExpDate))
       OR (:effDate = p.effDate)
       OR (:expDate = p.expDate)
       OR (:effDate <= p.effDate AND :expDate > p.effDate)
       OR (:expDate > p.effDate AND :expDate < COALESCE(p.expDate, :defaultExpDate))
    )
    AND p.ratePlanId = :ratePlanId
    AND (:mappingId IS NULL OR p.mappingId = :mappingId)
    AND p.id <> :priceVerId
""")
    List<PriceVer> findConflictsWithExpDate(
            @Param("effDate") LocalDate effDate,
            @Param("expDate") LocalDate expDate,
            @Param("defaultExpDate") LocalDate defaultExpDate, // ⬅️ tambahan
            @Param("ratePlanId") Integer ratePlanId,
            @Param("mappingId") Integer mappingId,
            @Param("priceVerId") Integer priceVerId
        );



//       @Modifying
//       @Query("DELETE FROM PriceVer pv WHERE pv.id = :priceVerId")
//       void deletePriceVerById(@Param("priceVerId") Long priceVerId);

}
