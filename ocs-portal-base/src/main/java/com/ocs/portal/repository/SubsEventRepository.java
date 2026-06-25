package com.ocs.portal.repository;

import com.ocs.portal.entity.SubsEvent;
import com.ocs.portal.mapper.pricePlan.trigger.QrySubsEvent;
import com.ocs.portal.projection.pricePlan.BalSubsEventProjection;
import com.ocs.portal.projection.subs.SelectSubsEventProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubsEventRepository extends JpaRepository<SubsEvent, Integer> {

  @Query(value = """
      SELECT A.SUBS_EVENT_ID as subsEventId,
             A.BAL_THRESHOLD_ID as balThresholdId,
             B.EVENT_NAME as eventName,
             A.REF_ATTR as refAttr,
             A.TRIGGER_MODE as triggerMode,
             A.ANTIBILL_SHOCK as antibillShock,
             A.EXT_ATTR as extAttr,
             A.NOTIFY_PARAMS_ID as notifyParamsId
      FROM BAL_SUBS_EVENT A
      JOIN SUBS_EVENT B ON A.SUBS_EVENT_ID = B.SUBS_EVENT_ID
      WHERE (:balThresholdId IS NULL OR A.BAL_THRESHOLD_ID = :balThresholdId)
        AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId)
      """, nativeQuery = true)
  List<BalSubsEventProjection> findBalSubsEvents(
      @Param("balThresholdId") Long balThresholdId,
      @Param("spId") Integer spId);

  @Query(value = """
      SELECT
      	SUBS_EVENT_ID as subsEventId,
      	EVENT_NAME as eventName,
      	COMMENTS as comments,
      	PRIORITY as priority,
      	STATE_SET as stateSet
      FROM
      	SUBS_EVENT
      ORDER BY
      	EVENT_NAME
      """, nativeQuery = true)
  List<QrySubsEvent> qrySubsEvent();

  @Query(value = "select s from SubsEvent s where s.subsEventId =:subsEventId")
  Optional<SubsEvent> selectBySubsEventId(@Param("subsEventId") Long subsEventId);

  @Query(value = """
      SELECT
          SUBS_EVENT_ID AS subsEventId,
          EVENT_NAME    AS eventName,
          COMMENTS      AS comments,
          PRIORITY      AS priority,
          STATE_SET     AS stateSet,
          EVENT_CATG    AS eventCatg,
          OBJ_PROD_STATE AS objProdState
      FROM SUBS_EVENT
      WHERE SUBS_EVENT_ID = :subsEventId
      """, nativeQuery = true)
  Optional<SelectSubsEventProjection> selectSubsEvent(
      @Param("subsEventId") Long subsEventId);
}
