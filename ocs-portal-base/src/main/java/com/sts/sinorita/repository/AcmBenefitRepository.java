package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcmBenefit;
import com.sts.sinorita.dto.response.priceplan.AcmBenefitResponseDto;
import com.sts.sinorita.entity.AcmBenefitId;
import com.sts.sinorita.projection.pricePlan.trigger.AcmBenefitProjection;
import com.sts.sinorita.projection.pricePlan.trigger.QryAcmBenefitProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcmBenefitRepository extends JpaRepository<AcmBenefit, AcmBenefitId> {

    @Query(value = "SELECT new com.sts.sinorita.dto.response.priceplan.AcmBenefitResponseDto(ab.id.acmThresholdId,ab.id.subBalTypeId,ab.value, ab.spId) " +
            "FROM AcmBenefit ab " +
            "WHERE ab.id.acmThresholdId =:acmThresholdId AND ab.id.subBalTypeId= :subBalTypeId ")
    public List<AcmBenefitResponseDto> findAcmBenefitResponseDto(@Param("acmThresholdId") Integer acmThresholdId,
                                                                 @Param("subBalTypeId") Integer subBalTypeId,
                                                                 Pageable pageable);

    @Query(value = "SELECT ab  " +
            "FROM AcmBenefit ab " +
            "WHERE ab.id.acmThresholdId =:acmThresholdId AND ab.id.subBalTypeId= :subBalTypeId ")
    public List<AcmBenefit> findAcmBenefitByTreshold(@Param("acmThresholdId") Integer acmThresholdId,
                                                                 @Param("subBalTypeId") Integer subBalTypeId);

    @Query(value = """
            SELECT ar.ACCT_RES_NAME as acctResName, ab.VALUE as benefitValue FROM SUB_BAL_TYPE sbt
            JOIN ACCT_RES ar ON sbt.ACCT_RES_ID = ar.ACCT_RES_ID\s
            JOIN ACM_BENEFIT ab ON sbt.SUB_BAL_TYPE_ID = ab.SUB_BAL_TYPE_ID\s
            WHERE ab.ACM_THRESHOLD_ID = :thresholdId
            """, nativeQuery = true)
    List<AcmBenefitProjection> getListTriggerBenefit(@Param("thresholdId") Integer thresholdId);

    @Query(value = """
            SELECT * FROM ACM_BENEFIT ab
            WHERE ab.SUB_BAL_TYPE_ID = :subBalTypeId
            """, nativeQuery = true)
    AcmBenefit findBySubBalTypeId(Integer subBalTypeId);


//    @Query(value = """
//            SELECT\s
//              A.id.acmThresholdId AS acmThresholdId,
//              A.id.subBalTypeId AS subBalTypeId,
//              A.value AS value,
//              B.acctResId AS acctResId,
//              B.priority AS priority,
//              B.periodId AS periodId,
//              B.maxDays as maxDays,
//              B.ceilLimit AS ceilLimit,
//              B.floorLimit AS floorLimit,
//              B.dailyCeilLimit AS dailyCeilLimit,
//              B.dailyFloorLimit AS dailyFloorLimit,
//              B.periodRelUnit AS periodRelUnit,
//              B.absExpOffset AS absExpOffset,
//              B.extendRule AS extendRule,
//              B.limitSubs AS limitSubs,
//              C.acctResName AS acctResName,
//              C.isCurrency AS isCurrency,
//              C.comments AS comments,
//              C.balType AS balType,
//              C.creditLimit AS creditLimit,
//              C.remindDay AS remindDay,
//              C.remindValue AS remindValue,
//              C.maxValue AS maxValue,
//              D.absEffDate AS absEffDate,
//              D.absExpDate AS absExpDate,
//              D.relEffOffset AS relEffOffset,
//              D.relEffUnit AS relEffUnit,
//              D.relExpOffset AS relExpOffset,
//              D.relExpUnit AS relExpUnit,
//              D.relEffTime AS relEffTime,
//              D.relExpTime AS relExpTime,
//              D.dayOffset AS dayOffset,
//              CASE\s
//                      WHEN D.absEffDate IS NOT NULL AND D.absExpDate IS NOT NULL THEN 'absolute'
//                      ELSE 'relative'
//                  END AS periodType
//            FROM AcmBenefit A
//            JOIN SubBalType B ON A.id.subBalTypeId = B.id
//            JOIN AcctRes C ON B.acctResId = C.acctResId
//            JOIN Period D ON B.periodId = D.id
//            WHERE (:acmThresholdId IS NULL OR A.id.acmThresholdId = :acmThresholdId)
//            AND (:subBalTypeId IS NULL OR A.id.subBalTypeId = :subBalTypeId)
//            AND (:spId IS NULL OR NVL(A.spId, 0) = :spId)
//            """)
//    List<QryAcmBenefitProjection> qryAcmBenefitList(@Param("acmThresholdId") Integer acmThresholdId,
//                                                    @Param("subBalTypeId") Integer subBalTypeId,
//                                                    @Param("spId") Integer spId);


    @Query(value = """
    SELECT
        A.ACM_THRESHOLD_ID AS acmThresholdId,
        A.SUB_BAL_TYPE_ID AS subBalTypeId,
        A.VALUE AS value,
        B.ACCT_RES_ID AS acctResId,
        B.PRIORITY AS priority,
        B.PERIOD_ID AS periodId,
        B.MAX_DAYS AS maximumDays,
        B.CEIL_LIMIT AS ceilLimit,
        B.FLOOR_LIMIT AS floorLimit,
        B.DAILY_CEIL_LIMIT AS dailyCeilLimit,
        B.DAILY_FLOOR_LIMIT AS dailyFloorLimit,
        B.PERIOD_REL_UNIT AS periodRelUnit,
        B.ABS_EXP_OFFSET AS absExpOffset,
        B.EXTEND_RULE AS extendRule,
        B.LIMIT_SUBS AS limitSubs,
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
        D.DAY_OFFSET AS dayOffset,
        CASE
            WHEN D.ABS_EFF_DATE IS NOT NULL AND D.ABS_EXP_DATE IS NOT NULL THEN 'absolute'
            ELSE 'relative'
        END AS periodType
    FROM ACM_BENEFIT A
    JOIN SUB_BAL_TYPE B ON A.SUB_BAL_TYPE_ID = B.SUB_BAL_TYPE_ID
    JOIN ACCT_RES C ON B.ACCT_RES_ID = C.ACCT_RES_ID
    JOIN PERIOD D ON B.PERIOD_ID = D.PERIOD_ID
    WHERE (:acmThresholdId IS NULL OR A.ACM_THRESHOLD_ID = :acmThresholdId)
      AND (:subBalTypeId IS NULL OR A.SUB_BAL_TYPE_ID = :subBalTypeId)
      AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId)
""", nativeQuery = true)
    List<QryAcmBenefitProjection> qryAcmBenefitList(
            @Param("acmThresholdId") Integer acmThresholdId,
            @Param("subBalTypeId") Integer subBalTypeId,
            @Param("spId") Integer spId
    );

    @Modifying
    @Query(value = "DELETE ACM_BENEFIT WHERE ACM_THRESHOLD_ID= :tresholdId AND SUB_BAL_TYPE_ID=:subBalTypeId ",nativeQuery = true)
    public void deleteAcmBenefit(@Param("tresholdId")Integer tresholdId, @Param("subBalTypeId") Integer subBalTypeId);

    @Modifying
    @Query(value = "DELETE SUB_BAL_TYPE WHERE SUB_BAL_TYPE_ID= :subBalTypeId", nativeQuery = true)
    public void deleteSubBalType(@Param("subBalTypeId") Integer subBalTypeId);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
            FROM ACM_BENEFIT 
            WHERE ACM_THRESHOLD_ID = :thresholdId
            """, nativeQuery = true)
    boolean isReferencedInBenefit(@Param("thresholdId") Long thresholdId);

}
