package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcmSubsEvent;
import com.sts.sinorita.projection.pricePlan.price.QryAcmSubsEventProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcmSubsEventRepository extends JpaRepository<AcmSubsEvent, Integer> {

    @Query(value = """
            SELECT
            	A.id.acmThresholdId AS acmThresholdId,
            	A.id.subsEventId AS subsEventId,
            	B.eventName AS eventName,
            	A.extAttr AS extAttr,
            	A.triggerMode AS triggerMode,
            	A.antibillShock AS antibillShock,
            	A.notifyParamsId AS notifyParamsId
            FROM
            	AcmSubsEvent A,
            	SubsEvent B
            WHERE
            	A.id.subsEventId = B.subsEventId
            	AND A.id.acmThresholdId = :ACM_THRESHOLD_ID
            	AND NVL(A.spId, 0) = :SP_ID
            """)
    List<QryAcmSubsEventProjection> qryAcmSubsEvent(@Param("ACM_THRESHOLD_ID") Integer acmThresholdId, @Param("SP_ID") Integer spId);

    @Modifying
    @Query(value = "DELETE ACM_SUBS_EVENT WHERE ACM_THRESHOLD_ID=:ACM_THRESHOLD_ID AND SUBS_EVENT_ID=:SUBS_EVENT_ID", nativeQuery = true)
    void deleteSubEvent(@Param("ACM_THRESHOLD_ID")Integer ACM_THRESHOLD_ID,@Param("SUBS_EVENT_ID")Integer SUBS_EVENT_ID);

    @Query(value = "select a from AcmSubsEvent a where a.id.acmThresholdId =:acmThresholdId and a.id.subsEventId =:subsEventId")
    Optional<AcmSubsEvent> findByTresholdIdAndSubsEventId(@Param("acmThresholdId")Integer acmThresholdId,@Param("subsEventId")Integer subsEventId);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
            FROM ACM_SUBS_EVENT 
            WHERE ACM_THRESHOLD_ID = :thresholdId
            """, nativeQuery = true)
    boolean isReferencedInSubsEvent(@Param("thresholdId") Long thresholdId);

}
