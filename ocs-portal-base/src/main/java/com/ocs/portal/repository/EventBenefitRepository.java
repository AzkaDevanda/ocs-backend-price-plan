package com.ocs.portal.repository;

import com.ocs.portal.dto.response.priceVer.GetDetailBenefitPriceProjection;
import com.ocs.portal.dto.response.priceVer.PriceBenefitProjection;
import com.ocs.portal.entity.EventBenefit;
import com.ocs.portal.projection.pricePlan.price.QryEventBenefitProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventBenefitRepository extends JpaRepository<EventBenefit, Integer> {

    @Query("SELECT COALESCE(MAX(t.priority), 0) + 1 FROM EventBenefit t WHERE t.priceVerId = :priceVerId")
    Integer findNextPriority(@Param("priceVerId") Integer priceVerId);

    @Modifying
    @Transactional
    @Query("update EventBenefit set value = :refValueId where id = :id")
    void insertValue(@Param("refValueId") Integer refValueId, @Param("id") Integer id);

    @Query("SELECT eb FROM EventBenefit eb WHERE eb.priceVerId = :priceVerId")
    Optional<EventBenefit> findEventBenefitByPriceVerId(@Param("priceVerId") Integer priceVerId);

    @Query(value = """
            SELECT 
                r.RE_TYPE as reType,
                pv.PRICE_VER_ID AS priceVerId,
                pv.EFF_DATE AS effDate,
                pv.EXP_DATE AS expDate,
                eb.PRICE_ID AS priceId,
                eb.SUB_BAL_TYPE_ID AS subBalTypeId,
                eb.PRICE_NAME AS priceName,
                rv.VALUE_STRING AS valueString,
                eb.RUM AS rum,
                ar.ACCT_RES_NAME AS acctResName,
                ra.RE_ATTR_NAME AS reAttrName
            FROM PRICE_VER pv
            JOIN RATE_PLAN rp ON pv.RATE_PLAN_ID = rp.RATE_PLAN_ID
            JOIN EVENT_BENEFIT eb ON pv.PRICE_VER_ID = eb.PRICE_VER_ID
            JOIN RE_ATTR ra ON eb.RE_ATTR = ra.RE_ATTR
            JOIN REF_VALUE rv ON eb.VALUE = rv.REF_VALUE_ID
            JOIN RE r ON rp.RE_ID  = r.RE_ID\s            
            JOIN SUB_BAL_TYPE sbt ON eb.SUB_BAL_TYPE_ID = sbt.SUB_BAL_TYPE_ID
            JOIN ACCT_RES ar ON sbt.ACCT_RES_ID = ar.ACCT_RES_ID
            WHERE rp.RATE_PLAN_ID = :ratePlanId AND rp.RATE_PLAN_TYPE = 3
            """, nativeQuery = true)
    List<PriceBenefitProjection> findPriceVerByRatePlanId(@Param("ratePlanId") Integer ratePlanId);

    @Query(value = """
            SELECT
                eb.PRICE_ID AS priceId,
                pv.EFF_DATE AS effectiveDate,
                pv.EXP_DATE AS expiryDate,
                eb.PRICE_NAME AS benefitName,
                eb.COMMENTS AS remarks,
                eb.CONFIG_TYPE AS configType,
                rv.VALUE_STRING AS benefitValue,
                ar.ACCT_RES_ID AS acctBalanceTypeId,
                ar.ACCT_RES_NAME AS acctBalanceTypeName,
                eb.RUM AS calculationUnit,
                ra.RE_ATTR AS reAttr,
                ra.RE_ATTR_NAME AS reAttrName,
                sbt.FLOOR_LIMIT AS cycleFloorLimit,
                sbt.BAL_FLAGS AS balFlags,
                sbt.CEIL_LIMIT AS cycleCeilLimit,
                sbt.DAILY_FLOOR_LIMIT AS dailyFloorLimit,
                sbt.DAILY_CEIL_LIMIT AS dailyCeilLimit,
                sbt.MAX_DAYS AS maximumDays,
                sbt.LIMIT_SUBS AS subscriberOnly,
                p.ABS_EFF_DATE AS absoluteEffectiveDate,
                p.ABS_EXP_DATE AS absoluteExpiryDate,
                p.REL_EFF_OFFSET AS offsetOfEffectiveDate,
                p.REL_EFF_UNIT AS offsetOfEffectiveDateUnit,
                p.REL_EXP_OFFSET AS durationOfAvailability,
                p.REL_EXP_UNIT AS durationOfAvailabilityUnit,
                p.REL_EFF_TIME AS relativeEffectiveTime,
                p.REL_EXP_TIME AS relativeExpiryTime,
                sbt.PERIOD_REL_UNIT AS relativePeriodUnit,
                sbt.ABS_EXP_OFFSET AS offsetOfAbsoluteExpiry,
                eb.RULE_SCRIPT AS rule,
                eb.RULE_COMMENTS AS ruleRemarks,
                eb.SCRIPT_PAGE AS scriptPage,
                eb.SCRIPT_TEMPLET_ID AS scriptTempletId
            FROM
                EVENT_BENEFIT eb
            JOIN PRICE_VER pv ON eb.PRICE_VER_ID = pv.PRICE_VER_ID
            JOIN REF_VALUE rv ON eb.VALUE = rv.REF_VALUE_ID
            JOIN SUB_BAL_TYPE sbt ON eb.SUB_BAL_TYPE_ID = sbt.SUB_BAL_TYPE_ID
            JOIN PERIOD p ON sbt.PERIOD_ID = p.PERIOD_ID
            JOIN RE_ATTR ra ON eb.RE_ATTR = ra.RE_ATTR
            JOIN ACCT_RES ar ON sbt.ACCT_RES_ID = ar.ACCT_RES_ID
            WHERE
                eb.PRICE_ID = :priceId
            """, nativeQuery = true)
    Optional<GetDetailBenefitPriceProjection> findDetailBenefitPrice(@Param("priceId") Integer priceId);

    @Query(value = """
            SELECT
                A.PRICE_ID as priceId,
                A.PRICE_NAME as priceName,
                A.PRICE_VER_ID as priceVerId,
                A.SUB_BAL_TYPE_ID as subBalTypeId,
                A.PRIORITY as priority,
                A.SCRIPT_PAGE as scriptPage,
                K.VALUE_STRING as value,
                A.CONFIG_TYPE as configType,
                A.RE_ATTR as reAttr,
                G.RE_ATTR_NAME as reAttrName,
                A.RUM as rum,
                A.CALC_PRECISION as calcPrecision,
                A.RULE_SCRIPT as ruleScript,
                A.RULE_COMMENTS as ruleComments,
                A.SCRIPT_TEMPLET_ID as scriptTempletId,
                A.REPEAT_CNT as repeatCnt,
                B.PERIOD_ID as periodId,
                B.ACCT_RES_ID as acctResId,
                D.IS_CURRENCY as isCurrency,
                D.ACCT_RES_NAME as acctResName,
                C.REL_EFF_UNIT as offsetOfEffectiveDateUnit,
                C.REL_EXP_UNIT as durationOfAvailabilityUnit,
                E1.TIME_UNIT AS REL_EFF_UNIT_NAME,
                E2.TIME_UNIT AS REL_EXP_UNIT_NAME,
                F.EFF_DATE as effectiveDate,
                F.EXP_DATE as expiryDate,
                A.SHARE_FLAG as shareFlag,
                F.RATE_PLAN_ID as ratePlanId,
                H.RATE_PLAN_TYPE as ratePlanType,
                K.OFFER_VER_ID as offerVerId,
                F.MAPPING_ID as mappingId
            FROM EVENT_BENEFIT A
            JOIN SUB_BAL_TYPE B ON A.SUB_BAL_TYPE_ID = B.SUB_BAL_TYPE_ID
            JOIN PERIOD C ON B.PERIOD_ID = C.PERIOD_ID
            JOIN ACCT_RES D ON B.ACCT_RES_ID = D.ACCT_RES_ID
            LEFT JOIN PRICE_VER F ON A.PRICE_VER_ID = F.PRICE_VER_ID
            LEFT JOIN RATE_PLAN H ON F.RATE_PLAN_ID = H.RATE_PLAN_ID
            LEFT JOIN TIME_UNIT E1 ON C.REL_EFF_UNIT = E1.TIME_UNIT
            LEFT JOIN TIME_UNIT E2 ON C.REL_EXP_UNIT = E2.TIME_UNIT
            JOIN RE_ATTR G ON A.RE_ATTR = G.RE_ATTR
            LEFT JOIN REF_VALUE K ON A.VALUE = K.REF_VALUE_ID
            WHERE
                (:SP_ID IS NULL OR NVL(A.SP_ID, 0) = :SP_ID)
                 AND (:RATE_PLAN_ID IS NULL OR F.RATE_PLAN_ID = :RATE_PLAN_ID)
                 AND (:PRICE_VER_ID IS NULL OR A.PRICE_VER_ID = :PRICE_VER_ID)
                 AND (:MAPPING_ID IS NULL OR F.MAPPING_ID = :MAPPING_ID)
                 AND (:PRICE_ID IS NULL OR A.PRICE_ID = :PRICE_ID)
                -- AND A.SRC_PRICE_ID = :SRC_PRICE_ID
                -- AND A.SHARE_FLAG = :SHARE_FLAG
            """, nativeQuery = true)
    List<QryEventBenefitProjection> qryEventBenefit(@Param("PRICE_ID") Integer PRICE_ID,
                                                    @Param("RATE_PLAN_ID") Integer RATE_PLAN_ID,
                                                    @Param("PRICE_VER_ID") Integer PRICE_VER_ID,
                                                    @Param("MAPPING_ID") Integer MAPPING_ID,
                                                    @Param("SP_ID") Integer SP_ID);

    @Query(
            value = "SELECT COUNT(A.PRICE_ID) FROM EVENT_BENEFIT A WHERE A.PRICE_VER_ID = :priceVerId",
            nativeQuery = true
    )
    int selectPriceCountByPriceVer(@Param("priceVerId") Integer priceVerId);


    @Transactional
    @Modifying
    @Query("UPDATE EventBenefit p SET p.priority = p.priority + :addNum WHERE p.priceVerId = :priceVerId AND p.priority BETWEEN :beginPriority AND :endPriority")
    int updateBenefitPriorityByArr(@Param("addNum") Integer addNum, @Param("beginPriority") Integer beginPriority, @Param("endPriority") Integer endPriority, @Param("priceVerId") Integer priceVerId);

}
