package com.sts.sinorita.repository;

import com.sts.sinorita.entity.OverdueItem;
import com.sts.sinorita.entity.OverdueItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OverdueItemRepository extends JpaRepository<OverdueItem, OverdueItemId> {
  @Transactional
  @Modifying
  @Query(value = """
    INSERT INTO OVERDUE_ITEM
    (EVENT_INST_ID, SEQ, BILLING_CYCLE_ID, ACCT_ITEM_TYPE_ID,
     CHARGE, OVERDUE_CHARGE, OVERDUE_ADJUST_ID, ADJUST_CHARGE, SP_ID)
    VALUES
    (:eventInstId, :seq, :billingCycleId, :acctItemTypeId,
     :charge, :overdueCharge, :overdueAdjustId, :adjustCharge, :spId)
    """, nativeQuery = true)
  void overdueItemBatchStore (
    @Param("eventInstId") Long eventInstId,
    @Param("seq") Integer seq,
    @Param("billingCycleId") Long billingCycleId,
    @Param("acctItemTypeId") Long acctItemTypeId,
    @Param("charge") Long charge,
    @Param("overdueCharge") Long overdueCharge,
    @Param("overdueAdjustId") Long overdueAdjustId,
    @Param("adjustCharge") Long adjustCharge,
    @Param("spId") Long spId
  );
}
