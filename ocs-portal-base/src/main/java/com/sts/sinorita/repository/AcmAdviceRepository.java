package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcmAdvice;
import com.sts.sinorita.projection.pricePlan.trigger.QryAcmAdviceProjection;
import com.sts.sinorita.projection.trigger.AdviceEventProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcmAdviceRepository extends JpaRepository<AcmAdvice, Integer> {
    @Query(value = """
            SELECT
                A.acmThresholdId AS acmThresholdId,
                A.adviceType AS adviceType,
                B.adviceTypeName AS adviceTypeName,
                A.triggerMode AS triggerMode,
                C.id AS adviceEventId,
                C.adviceEventName AS adviceEventName,
                A.notifyParamsId AS notifyParamsId,
                CASE\s
                    WHEN C.id IS NULL THEN 'notifType'
                    ELSE 'adviceEvent '
                END AS triggerNotification
            FROM
                AcmAdvice A
                LEFT JOIN AdviceType B ON A.adviceType = B.id
                LEFT JOIN AdviceEvent C ON A.adviceEventId = C.id
            WHERE
                A.acmThresholdId = :ACM_THRESHOLD_ID
                AND NVL(A.spId, 0) = :SP_ID
            """)
    List<QryAcmAdviceProjection> qryAcmAdvice(@Param("ACM_THRESHOLD_ID") Integer acmThresholdId, @Param("SP_ID") Integer spId);

    @Query(value = """
                SELECT 
                    T.ADVICE_EVENT_ID   AS adviceEventId,
                    T.ADVICE_EVENT_CODE AS adviceEventCode,
                    T.ADVICE_EVENT_NAME AS adviceEventName,
                    T.COMMENTS          AS comments,
                    T.STATE             AS state,
                    T.STATE_DATE        AS stateDate,
                    T.SP_ID             AS spId
                FROM 
                    ADVICE_EVENT T
                WHERE 
                    T.STATE = 'A'
            """, nativeQuery = true)
    List<AdviceEventProjection> findActiveAdviceEvents();

    @Modifying
    @Query(value = """
            DELETE ACM_ADVICE WHERE ACM_THRESHOLD_ID=:acmThresholdId 
            AND (:adviceType IS NULL OR ADVICE_TYPE = :adviceType)
            """, nativeQuery = true)
    void deleteAcmAdvice(@Param("acmThresholdId")Integer acmThresholdId
            ,@Param("adviceType")Integer adviceType);

    @Query(value = """
            SELECT * FROM ACM_ADVICE WHERE ACM_THRESHOLD_ID=:acmThresholdId
            """,nativeQuery = true)
    List<AcmAdvice> getAcmAdvice(@Param("acmThresholdId")Integer acmThresholdId);

//            ,@Param("notifParamId")Integer notifParamId
//    AND (:adviceEventId IS NULL OR ADVICE_EVENT_ID = :adviceEventId)
//AND (:notifParamId IS NULL OR NOTIFY_PARAMS_ID = :notifParamId)

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ACM_ADVICE " +
            "(ACM_THRESHOLD_ID, ADVICE_TYPE, ADVICE_EVENT_ID, SP_ID, TRIGGER_MODE, NOTIFY_PARAMS_ID) " +
            "VALUES (:thresholdId, :adviceType, :eventId, :spId, :triggerMode, :notifyParamsId)", nativeQuery = true)
    void insertAdvice(@Param("thresholdId") Integer thresholdId,
                      @Param("adviceType") Integer adviceType,
                      @Param("eventId") Integer eventId,
                      @Param("spId") Integer spId,
                      @Param("triggerMode") Character triggerMode,
                      @Param("notifyParamsId") Integer notifyParamsId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ACM_ADVICE SET SP_ID = :spId " +
            "WHERE ACM_THRESHOLD_ID = :thresholdId AND ADVICE_TYPE = :adviceType AND ADVICE_EVENT_ID = :eventId",
            nativeQuery = true)
    void updateSpId(@Param("spId") Integer spId,
                   @Param("thresholdId") Integer thresholdId,
                   @Param("adviceType") Integer adviceType,
                   @Param("eventId") Integer eventId);

    @Modifying
    @Query("""
    DELETE FROM AcmAdvice a
    WHERE a.acmThresholdId = :acmThresholdId
      AND (
        (:adviceType IS NOT NULL AND a.adviceType = :adviceType)
        OR (:adviceType IS NULL AND a.adviceEventId = :adviceEventId)
      )
      AND (
        (:notifyParamsId IS NOT NULL AND a.notifyParamsId = :notifyParamsId)
        OR (:notifyParamsId IS NULL AND a.notifyParamsId IS NULL)
      )
    """)
    void deleteByDynamicConditions(
            @Param("acmThresholdId") Integer acmThresholdId,
            @Param("adviceType") Integer adviceType,
            @Param("adviceEventId") Integer adviceEventId,
            @Param("notifyParamsId") Integer notifyParamsId
    );

    @Query(value = """
    SELECT CASE 
             WHEN COUNT(*) > 0 THEN true 
             ELSE false 
           END
    FROM ACM_ADVICE a
    WHERE a.ACM_THRESHOLD_ID = :acmThresholdId
      AND (
        (:adviceType IS NOT NULL AND a.ADVICE_TYPE = :adviceType)
        OR (:adviceType IS NULL AND a.ADVICE_EVENT_ID = :adviceEventId)
      )
      AND (
        (:notifyParamsId IS NOT NULL AND a.NOTIFY_PARAMS_ID = :notifyParamsId)
        OR (:notifyParamsId IS NULL AND a.NOTIFY_PARAMS_ID IS NULL)
      )
    """, nativeQuery = true)
    boolean existsByDynamicConditions(
            @Param("acmThresholdId") Integer acmThresholdId,
            @Param("adviceType") Integer adviceType,
            @Param("adviceEventId") Integer adviceEventId,
            @Param("notifyParamsId") Integer notifyParamsId
    );

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
            FROM ACM_ADVICE 
            WHERE ACM_THRESHOLD_ID = :thresholdId
            """, nativeQuery = true)
    boolean isReferencedInAdvice(@Param("thresholdId") Long thresholdId);

}
