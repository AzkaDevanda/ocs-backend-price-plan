package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcmThreshold;
import com.sts.sinorita.projection.pricePlan.trigger.QryAcmThresholdProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcmThresholdRepository extends JpaRepository<AcmThreshold, Integer> {
    @Query(value = "SELECT acm FROM AcmThreshold acm WHERE acm.triggerId =:triggerId and acm.value =:value")
    public Optional<AcmThreshold> doFindByTriggerIdAndValue(@Param("triggerId") Integer triggerId,
                                                            @Param("value") Long value);

    @Query(value = """
             SELECT A.id acmThresholdId, A.triggerId triggerId, A.value value, A.interval interval, A.reAttr reAttr,
                    C.reAttrName reAttrName, COALESCE(A.ratio, 0) AS ratio, CASE WHEN B.acmThresholdId IS NULL THEN 'N' ELSE 'Y' END touchPcrf,
                    B.triggerMode triggerMode,D.id acmBillShockRuleId,D.ruleName ruleName,A.onOffAttr onOffAttr\s
             FROM AcmThreshold A
             LEFT JOIN AcmPcrf B ON A.id = B.acmThresholdId
             LEFT JOIN ReAttr C ON A.reAttr = C.id
             LEFT JOIN AcmBillShockRule E ON A.id = E.acmThresholdId
             LEFT JOIN BillShockRule D ON E.billShockRuleId = D.id
             WHERE 1 = 1
             AND (:triggerId IS NULL OR A.triggerId = :triggerId)
             AND (:acmThresholdId IS NULL OR A.id = :acmThresholdId)
             AND (:spId IS NULL OR COALESCE(A.spId, 0) = :spId)
            \s""")
    List<QryAcmThresholdProjection> qryAcmThreshold(@Param("triggerId") Integer triggerId, @Param("acmThresholdId") Integer acmThresholdId, @Param("spId") Integer spId);


    @Modifying
    @Query(value = "DELETE FROM ACM_THRESHOLD WHERE ACM_THRESHOLD_ID IN (:thresholdId)", nativeQuery = true)
    void deleteByThresholdId(@Param("thresholdId") Long thresholdId);

    @Query(value = "select b from AcmThreshold b where b.triggerId = :triggerId")
    public List<AcmThreshold> findByTriggerId(@Param("triggerId") Integer triggerId);

}
