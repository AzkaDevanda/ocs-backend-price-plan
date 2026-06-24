package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BalThreshold;
import com.sts.sinorita.projection.pricePlan.priceplanpackage.BalAdviceProjection;
import com.sts.sinorita.projection.pricePlan.priceplanpackage.BalBenefitProjection;
import com.sts.sinorita.projection.pricePlan.priceplanpackage.BalanceTresholdProjection;
import com.sts.sinorita.projection.pricePlan.priceplanpackage.DynReAttrProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceTresholdRepository extends JpaRepository<BalThreshold, Long> {

    @Query(value = """
            SELECT
                A.BAL_THRESHOLD_ID AS tresholdId,
                A.TRIGGER_ID AS triggerId,
                A.VALUE AS value,
                A.INTERVAL AS interval,
                A.RE_ATTR AS reAttr,
                C.RE_ATTR_NAME AS reattrName,
                A.RATIO AS ratio,
                CASE
                    WHEN B.BAL_THRESHOLD_ID IS NULL OR B.BAL_THRESHOLD_ID = 0 THEN 'N'
                    ELSE 'Y'
                END AS touchPcrf,
                B.TRIGGER_MODE AS triggerMode
            FROM BAL_THRESHOLD A, BAL_PCRF B, RE_ATTR C
            WHERE 1 = 1
              AND A.BAL_THRESHOLD_ID = B.BAL_THRESHOLD_ID(+)
              AND A.RE_ATTR = C.RE_ATTR(+)
              AND A.TRIGGER_ID = :TRIGGER_ID
              AND NVL(A.SP_ID, 0) = :SP_ID
              AND (:tresholdId IS NULL OR A.BAL_THRESHOLD_ID = :tresholdId)
            """, nativeQuery = true)
    public List<BalanceTresholdProjection> getBalanceTreshold(@Param("TRIGGER_ID")Integer TRIGGER_ID, @Param("SP_ID")Integer SP_ID, @Param("tresholdId")Integer tresholdId);

    @Query(value = """
            SELECT A.BAL_THRESHOLD_ID AS balThresholdId,
                A.SUB_BAL_TYPE_ID AS subBalTypeId,
                A.VALUE AS value,
                B.ACCT_RES_ID AS acctResId,
                B.PRIORITY AS priority,
                B.PERIOD_ID AS periodId,
                B.CEIL_LIMIT AS ceilLimit,
                B.FLOOR_LIMIT AS floorLimit,
                B.DAILY_CEIL_LIMIT AS dailyCeilLimit,
                B.DAILY_FLOOR_LIMIT AS dailyFloorLimit,
                B.LIMIT_SUBS AS limitSubs,
                B.PERIOD_REL_UNIT AS periodRelUnit,
                B.ABS_EXP_OFFSET AS absExpOffset,
                B.EXTEND_RULE AS extendRule,
                B.MAX_DAYS AS maximumDays,
                C.ACCT_RES_NAME AS acctResName,
                C.IS_CURRENCY AS isCurrency,
                C.COMMENTS AS comments,
                C.BAL_TYPE AS balType,
                C.CREDIT_LIMIT AS creditLimit,
                C.REMIND_DAY AS remindDay,
                C.REMIND_VALUE AS remindValue,
                C.MAX_VALUE AS maxValue,
                D.ABS_EFF_DATE AS absEffDate,
                D.ABS_EXP_DATE AS absExpDate,
                D.REL_EFF_OFFSET AS relEffOffset,
                D.REL_EFF_UNIT AS relEffUnit,
                D.REL_EXP_OFFSET AS relExpOffset,
                D.REL_EXP_UNIT AS relExpUnit,
                D.REL_EFF_TIME AS relEffTime,
                D.REL_EXP_TIME AS relExpTime,
             CASE
            WHEN D.ABS_EFF_DATE IS NOT NULL AND D.ABS_EXP_DATE IS NOT NULL THEN 'absolute'
            ELSE 'relative'
        END AS periodType
            FROM BAL_BENEFIT A,  SUB_BAL_TYPE B,  ACCT_RES C,  PERIOD D
             WHERE A.SUB_BAL_TYPE_ID = B.SUB_BAL_TYPE_ID
               AND B.ACCT_RES_ID = C.ACCT_RES_ID
               AND B.PERIOD_ID = D.PERIOD_ID
              AND A.BAL_THRESHOLD_ID = :BAL_THRESHOLD_ID
              AND NVL(A.SP_ID,0)=:SP_ID
  """, nativeQuery = true)
    public List<BalBenefitProjection> getBalBenefit(@Param("BAL_THRESHOLD_ID") Integer BAL_THRESHOLD_ID, @Param("SP_ID")Integer SP_ID);


        @Query(value = """
         SELECT
            A.BAL_THRESHOLD_ID AS balThresholdId,
            A.ADVICE_TYPE AS adviceType,
            B.ADVICE_TYPE_NAME AS adviceTypeName,
            A.TRIGGER_MODE AS triggerMode,
            C.ADVICE_EVENT_ID AS adviceEventId,
            C.ADVICE_EVENT_NAME AS adviceEventName,
            A.NOTIFY_PARAMS_ID AS notifyParamsId,
            CASE
                WHEN C.ADVICE_EVENT_ID IS NOT NULL THEN 'notifEvent'
                ELSE 'notifType'
            END AS triggerNotification
                FROM
            BAL_ADVICE A
                LEFT JOIN ADVICE_TYPE B ON A.ADVICE_TYPE = B.ADVICE_TYPE
                LEFT JOIN ADVICE_EVENT C ON A.ADVICE_EVENT_ID = C.ADVICE_EVENT_ID
                WHERE
            1 = 1
            AND (:BAL_THRESHOLD_ID IS NULL OR A.BAL_THRESHOLD_ID = :BAL_THRESHOLD_ID)
            AND NVL(A.SP_ID, 0) = :SP_ID
            """, nativeQuery = true)
        public List<BalAdviceProjection> getListBalAdvice(@Param("BAL_THRESHOLD_ID")Integer BAL_THRESHOLD_ID ,@Param("SP_ID") Integer SP_ID);

       @Query (value = """
               SELECT\s
                   S.RE_ATTR AS reAttr,
                   S.DYN_ATTR_ID AS dynAttrId,
                   S.ATTR_CATG AS attrCatg,
                   S.DEPEND_PROD_SPEC_ID AS dependProdSpecId,
                   S.RE_TYPE AS reType,
                   S.RE_ATTR_NAME AS reAttrName,
                   S.COMMENTS AS comments,
                   S.MEASURABLE AS measurable,
                   S.ATTR_NAME AS attrName,
                   S.RE_TYPE_NAME AS reTypeName
               FROM (
                   -- Bagian pertama
                   SELECT
                       A.RE_ATTR,
                       A.DYN_ATTR_ID,
                       A.ATTR_CATG,
                       A.DEPEND_PROD_SPEC_ID,
                       B.RE_TYPE,
                       B.RE_ATTR_NAME,
                       B.COMMENTS,
                       B.MEASURABLE,
                       C.ATTR_NAME,
                       D.RE_TYPE_NAME
                   FROM DYN_RE_ATTR A
                   JOIN RE_ATTR B ON A.RE_ATTR = B.RE_ATTR
                   JOIN ATTR C ON A.DYN_ATTR_ID = C.ATTR_ID
                   LEFT JOIN RE_TYPE D ON B.RE_TYPE = D.RE_TYPE
                   WHERE 1 = 1
                     AND (:RE_TYPE IS NULL OR B.RE_TYPE = :RE_TYPE)
                     AND (:SP_ID IS NULL OR NVL(A.SP_ID, 0) = :SP_ID OR :SP_ID = -1)
                     AND (:RE_ATTR_NAME IS NULL OR UPPER(B.RE_ATTR_NAME) LIKE CONCAT('%', UPPER(:RE_ATTR_NAME), '%'))
               
                   UNION
               
                   -- Bagian kedua
                   SELECT
                       C.RE_ATTR,
                       NULL AS DYN_ATTR_ID,
                       NULL AS ATTR_CATG,
                       NULL AS DEPEND_PROD_SPEC_ID,
                       B.RE_TYPE,
                       B.RE_ATTR_NAME,
                       B.COMMENTS,
                       B.MEASURABLE,
                       NULL AS ATTR_NAME,
                       D.RE_TYPE_NAME
                   FROM DEF_RE_ATTR A
                   JOIN RE_ATTR B ON A.RE_ATTR = B.RE_ATTR
                   JOIN RESERVE_RE_ATTR C ON A.DEF_RE_ATTR = C.RE_ATTR
                   LEFT JOIN RE_TYPE D ON B.RE_TYPE = D.RE_TYPE
                   WHERE B.RE_ATTR_SRC_TYPE = 'A'
                     AND (:RE_TYPE IS NULL OR B.RE_TYPE = :RE_TYPE)
                     AND (:RE_ATTR_NAME IS NULL OR UPPER(B.RE_ATTR_NAME) LIKE CONCAT('%', UPPER(:RE_ATTR_NAME), '%'))
               ) S
               ORDER BY S.RE_ATTR_NAME
               """, nativeQuery = true)
    public List<DynReAttrProjection> getListDynAttrProjection(@Param("RE_ATTR_NAME") String RE_ATTR_NAME,@Param("RE_TYPE")Integer RE_TYPE, @Param("SP_ID")Integer SP_ID);


//    @Query(value = "SELECT BAL_THRESHOLD_ID,SUB_BAL_TYPE_ID,VALUE,SP_ID FROM BAL_BENEFIT WHERE BAL_THRESHOLD_ID=:BAL_THRESHOLD_ID AND SUB_BAL_TYPE_ID= :SUB_BAL_TYPE_ID", nativeQuery = true)
    @Query(value = "SELECT 1 FROM BAL_BENEFIT WHERE BAL_THRESHOLD_ID=:BAL_THRESHOLD_ID AND SUB_BAL_TYPE_ID= :SUB_BAL_TYPE_ID", nativeQuery = true)
    public Optional<Integer> selectBalBenefit(@Param("BAL_THRESHOLD_ID")Integer BAL_THRESHOLD_ID,@Param("SUB_BAL_TYPE_ID")Integer SUB_BAL_TYPE_ID);

    @Query(value = "select b from BalThreshold b where b.id = :id")
    Optional<BalThreshold> selectBalThreshold(@Param("id") Integer id);
}
