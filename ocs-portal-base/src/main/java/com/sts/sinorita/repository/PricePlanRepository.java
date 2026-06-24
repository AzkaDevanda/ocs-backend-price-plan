package com.sts.sinorita.repository;

import com.sts.sinorita.entity.PricePlan;
import com.sts.sinorita.projection.pricePlan.CopyFromProjection;
import com.sts.sinorita.projection.pricePlan.SelectPricePlanByPricePlanCodeProjection;
import com.sts.sinorita.projection.pricePlan.SortPricePlanIdByPriorityProjection;
import com.sts.sinorita.storeProcedure.PriceplanStoreProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PricePlanRepository extends JpaRepository<PricePlan, Integer>, PriceplanStoreProcedure {
  void findPricePlanById(Integer id);

  @Procedure(name = "delete_price_plan_id")
  void deletePricePlanProcedure(@Param("id") Integer id);

  @Query(value = "SELECT max(priority) FROM PRICE_PLAN", nativeQuery = true)
  Integer selectMaxPriority();

  @Modifying
  @Transactional
  @Query("UPDATE PricePlan p SET p.priority = :newPriority WHERE p.id = :pricePlanId")
  void updatePricePlanPriority(@Param("pricePlanId") Integer pricePlanId,
      @Param("newPriority") Integer newPriority);

  @Modifying
  @Transactional
  @Query("UPDATE PricePlan p SET p.priority = p.priority + :addNum WHERE p.priority BETWEEN :beginPriority AND :endPriority")
  void updatePricePlanPriorityByArr(@Param("addNum") Integer addNum,
      @Param("beginPriority") Integer beginPriority,
      @Param("endPriority") Integer endPriority);

  @Query(value = """
      SELECT A.PRICE_PLAN_ID AS pricePlanId,
             B.OFFER_NAME AS pricePlanName,
             D.OFFER_VER_ID AS offerVerId,
             D.EFF_DATE AS effDate,
             D.EXP_DATE AS expDate,
             D.SEQ AS seq
      FROM PRICE_PLAN A
      JOIN OFFER B ON A.PRICE_PLAN_ID = B.OFFER_ID
      JOIN SUBS_PRICE_PLAN C ON A.PRICE_PLAN_ID = C.PRICE_PLAN_ID
      JOIN OFFER_VER D ON B.OFFER_ID = D.OFFER_ID
      WHERE B.STATE = 'A'
        AND A.APPLY_LEVEL = 'S'
        AND (D.EFF_DATE < SYSDATE AND :showNotEffOfferver = 'N')
        AND (D.EXP_DATE > SYSDATE OR D.EXP_DATE IS NULL)
        AND NVL(A.SP_ID, 0) = :spId
        AND UPPER(B.OFFER_NAME) LIKE CONCAT('%', UPPER(:pricePlanName), '%')
      ORDER BY A.PRIORITY
      """, nativeQuery = true)
  List<CopyFromProjection> getCopyFromList(
      @Param("showNotEffOfferver") String showNotEffOfferver,
      @Param("spId") Integer spId,
      @Param("pricePlanName") String pricePlanName);

  @Query(value = """
      SELECT C.PRICE_PLAN_ID
      FROM SUBS_PRICE_PLAN B
      JOIN PRICE_PLAN C ON B.PRICE_PLAN_ID = C.PRICE_PLAN_ID
      JOIN OFFER D ON C.PRICE_PLAN_ID = D.OFFER_ID
      WHERE (B.PRICE_PLAN_TYPE = '1' OR B.PRICE_PLAN_TYPE = '6')
        AND D.STATE = 'A'
        AND (:spId IS NULL OR NVL(C.SP_ID, 0) = :spId)
      """, nativeQuery = true)
  List<Long> qrySystemDefPricePlan(@Param("spId") Long spId);

  @Query(value = """
          SELECT
              A.PRICE_PLAN_ID AS pricePlanId,
              MIN(A.PRIORITY) AS priority
          FROM PRICE_PLAN A
          WHERE A.PRICE_PLAN_ID IN (:pricePlanIds)
          GROUP BY A.PRICE_PLAN_ID
          ORDER BY priority
      """, nativeQuery = true)
  List<SortPricePlanIdByPriorityProjection> sortPricePlanIdByPriority(
      @Param("pricePlanIds") String pricePlanIds);

      @Query(value = """
    SELECT
        A.PRICE_PLAN_ID      AS pricePlanId,
        A.APPLY_LEVEL        AS applyLevel,
        B.OFFER_NAME         AS pricePlanName,
        B.COMMENTS           AS comments,
        B.STATE              AS state,
        B.STATE_DATE         AS stateDate,
        A.PRIORITY           AS priority,
        B.OFFER_CODE         AS pricePlanCode,
        A.SP_ID              AS spId
    FROM PRICE_PLAN A
    JOIN OFFER B
        ON A.PRICE_PLAN_ID = B.OFFER_ID
    WHERE B.OFFER_CODE = :pricePlanCode
    """, nativeQuery = true)
  Optional<SelectPricePlanByPricePlanCodeProjection> selectPricePlanByPricePlanCode(
        @Param("pricePlanCode") String pricePlanCode);
}
