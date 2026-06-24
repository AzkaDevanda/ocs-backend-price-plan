package com.sts.sinorita.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.PpsDueStateSubsEvent;
import com.sts.sinorita.projection.balanceAdjustment.SelectSubsEventIdForBalanceChangeTriggerProjection;

@Repository
public interface PpsDueStateSubsEventRepository extends JpaRepository<PpsDueStateSubsEvent, Long> {
  @Query(value = "SELECT DISTINCT A.SUBS_EVENT AS subsEvent, A.UNSUIT_CHANNEL_LIST AS unsuitChannelList, A.BLOCK_REASON AS blockReason FROM PPS_DUE_STATE_SUBS_EVENT A WHERE A.SRC_PROD_STATE = :prodState AND ( ( A.DUE_STATE_CHANGE IN (:dueStateChange) AND A.EXPIRE_DATE_CHANGE IS NULL ) OR ( A.DUE_STATE_CHANGE IS NULL AND A.EXPIRE_DATE_CHANGE = :expireDateChange ) OR ( A.DUE_STATE_CHANGE IN (:dueStateChange) AND A.EXPIRE_DATE_CHANGE = :expireDateChange ) )", nativeQuery = true)
  List<SelectSubsEventIdForBalanceChangeTriggerProjection> selectSubsEventIdForBalanceChangeTrigger(
      @Param("prodState") String prodState,
      @Param("dueStateChange") String dueStateChange,
      @Param("expireDateChange") String expireDateChange);
}
