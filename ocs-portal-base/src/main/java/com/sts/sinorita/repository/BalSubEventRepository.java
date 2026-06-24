package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BalSubsEvent;
import com.sts.sinorita.projection.pricePlan.trigger.BalSubsEventProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalSubEventRepository extends JpaRepository<BalSubsEvent, Integer> {

    @Query(value = "select b from BalSubsEvent b where b.id.balThresholdId = :balThresholdId and b.id.subsEventId =:subsEventId")
    public Optional<BalSubsEvent> findBalByBalThresholdId(@Param("balThresholdId") Integer balThresholdId, @Param("subsEventId")Integer subsEventId );


    @Query(value = """
            SELECT
  A.SUBS_EVENT_ID AS subsEventId,
  A.BAL_THRESHOLD_ID AS balThresholdId,
  B.EVENT_NAME AS eventName,
  A.REF_ATTR AS refAttr,
  A.TRIGGER_MODE AS triggerMode,
  A.ANTIBILL_SHOCK AS antibillShock,
  A.EXT_ATTR AS extAttr,
  A.NOTIFY_PARAMS_ID AS notifyParamsId
FROM BAL_SUBS_EVENT A, SUBS_EVENT B
WHERE A.SUBS_EVENT_ID = B.SUBS_EVENT_ID
  AND (:balThresholdId IS NULL OR A.BAL_THRESHOLD_ID = :balThresholdId)
  AND NVL(A.SP_ID, 0) = :spId
""",  nativeQuery = true)
    public List<BalSubsEventProjection> getListSubBalEvent(@Param("balThresholdId") Integer balThresholdId, @Param("spId") Integer spId);
}
