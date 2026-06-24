package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BalTrigger;
import com.sts.sinorita.projection.balanceAdjustment.SelectThresholdProjection;
import com.sts.sinorita.projection.pricePlan.trigger.BalTriggerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalTriggerRepository extends JpaRepository<BalTrigger, Integer> {
  @Query("SELECT CASE WHEN COUNT(bt) > 0 THEN 1 ELSE 0 END FROM BalTrigger bt JOIN TriggerType tt ON bt.triggerType = tt.id JOIN AcmTrigger at ON tt.id = at.triggerType JOIN OfferVer ov ON at.offerVerId = ov.id JOIN RePricePlan rpp ON ov.id = rpp.id.offerVerId JOIN RatePlan rp ON rpp.id.offerVerId = rp.offerVerId JOIN PriceVer pv ON rp.id = pv.ratePlanId WHERE pv.id = :priceVerId")
  int existsByPriceVerId(@Param("priceVerId") Integer priceVerId);

  @Query(value = "SELECT A.TRIGGER_ID AS triggerId, A.OFFER_VER_ID AS offerVerId, B.OFFER_ID AS offerId, B.OFFER_NAME AS offerName, A.ACCT_RES_ID AS acctResId, A.ACCT_RES_ID_LIST AS acctResIdList, A.TRIGGER_TYPE AS triggerType, D.TRIGGER_TYPE_NAME AS triggerTypeName, A.EFF_DATE AS effDate, A.STATE AS state, A.STATE_DATE AS stateDate, A.EXP_DATE AS expDate, A.IS_LIMIT_BALANCE AS isLimit, A.DESTINATION AS destination FROM BAL_TRIGGER A INNER JOIN OFFER_VER E ON A.OFFER_VER_ID = E.OFFER_VER_ID INNER JOIN OFFER B ON B.OFFER_ID = E.OFFER_ID LEFT JOIN TRIGGER_TYPE D ON A.TRIGGER_TYPE = D.TRIGGER_TYPE WHERE A.OFFER_VER_ID = :OFFER_VER_ID AND (:TRIGGER_ID IS NULL OR A.TRIGGER_ID = :TRIGGER_ID )", countQuery = "SELECT COUNT(*) FROM BAL_TRIGGER bt WHERE bt.OFFER_VER_ID = :OFFER_VER_ID", nativeQuery = true)
  Page<BalTriggerProjection> getBalTriggerByOfferVerId(@Param("TRIGGER_ID") Integer TRIGGER_ID, @Param("OFFER_VER_ID") Integer offerVerId, Pageable pageable);

  @Modifying
  @Query(value = "DELETE FROM BAL_THRESHOLD WHERE BAL_THRESHOLD_ID=:tresholdId", nativeQuery = true)
  void deleteBalTriggerByTresholdId(@Param("tresholdId") Integer tresholdId);

  @Modifying
  @Query(value = "DELETE BAL_PCRF WHERE BAL_THRESHOLD_ID=:tresholdId", nativeQuery = true)
  void deletePcrfTriggerByTresholdId(@Param("tresholdId") Integer tresholdId);

  @Query(value = "SELECT 1 FROM BAL_THRESHOLD WHERE TRIGGER_ID = :triggerId ", nativeQuery = true)
  public Optional<Integer> fieldIsReferencedBalTreshold(@Param("triggerId") Integer triggerId);

  @Modifying
  @Query(value = "DELETE FROM BAL_TRIGGER WHERE TRIGGER_ID= :triggerId", nativeQuery = true)
  void deleteBalTriggerByTriggerId(@Param("triggerId") Integer triggerId);

  @Query(value = "SELECT 1 FROM BAL_TRIGGER b WHERE b.ACCT_RES_ID = :acctResId FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
  Optional<Integer> isReferencedInAcctRes(@Param("acctResId") Long acctResId);

  @Query(value = "SELECT B.TRIGGER_ID AS triggerId, B.EFF_DATE AS triggerEffDate, B.ACCT_RES_ID_LIST AS acctResIdList, B.TRIGGER_TYPE AS triggerType, F.EFF_DATE AS priceEffDate, B.DESTINATION AS destination FROM BAL_TRIGGER B JOIN ( SELECT EFF_DATE, OFFER_VER_ID, OFFER_ID FROM OFFER_VER WHERE OFFER_ID = :pricePlanId AND (EXP_DATE IS NULL OR EXP_DATE > SYSDATE) ) F ON F.OFFER_VER_ID = B.OFFER_VER_ID WHERE B.STATE = 'A' AND (B.EXP_DATE IS NULL OR B.EXP_DATE > SYSDATE) AND EXISTS ( SELECT 1 FROM BAL_THRESHOLD C WHERE C.TRIGGER_ID = B.TRIGGER_ID )", nativeQuery = true)
  List<BalTriggerProjection> selectBalTrigger(@Param("pricePlanId") Long pricePlanId);

  @Query(value = "WITH BAL_TRIGGER_THRESHOLD AS ( SELECT C.VALUE AS thresholdValue, C.RATIO AS ratio, C.RE_ATTR AS reAttr, C.BAL_THRESHOLD_ID AS balThresholdId, B.TRIGGER_ID AS triggerId, B.ACCT_RES_ID AS acctResId, B.TRIGGER_TYPE AS triggerType, B.ACCT_RES_ID_LIST AS acctResIdList, F.OFFER_ID AS pricePlanId FROM BAL_TRIGGER B JOIN BAL_THRESHOLD C ON C.TRIGGER_ID = B.TRIGGER_ID JOIN OFFER_VER F ON F.OFFER_VER_ID = B.OFFER_VER_ID WHERE B.TRIGGER_ID IN (:triggerIds) ) SELECT D.SUBS_EVENT_ID AS subsEventId, D.EXT_ATTR AS extAttr, NULL AS adviceType, T.thresholdValue, T.ratio, T.reAttr, T.balThresholdId, T.triggerId, T.acctResId, T.triggerType, T.acctResIdList, T.pricePlanId FROM BAL_TRIGGER_THRESHOLD T LEFT JOIN BAL_SUBS_EVENT D ON D.BAL_THRESHOLD_ID = T.BAL_THRESHOLD_ID UNION ALL SELECT NULL AS subsEventId, NULL AS extAttr, E.ADVICE_TYPE AS adviceType, T.thresholdValue, T.ratio, T.reAttr, T.balThresholdId, T.triggerId, T.acctResId, T.triggerType, T.acctResIdList, T.pricePlanId FROM BAL_TRIGGER_THRESHOLD T LEFT JOIN BAL_ADVICE E ON E.BAL_THRESHOLD_ID = T.BAL_THRESHOLD_ID", nativeQuery = true)
  List<SelectThresholdProjection> selectThreshold(@Param("triggerIds") String triggerIds);
}
