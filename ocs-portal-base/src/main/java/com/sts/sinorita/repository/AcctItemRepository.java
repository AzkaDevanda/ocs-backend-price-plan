package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcctItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AcctItemRepository extends JpaRepository<AcctItem, Long> {
  boolean existsByAcctItemTypeId (Integer acctItemTypeId);

  @Transactional
  @Modifying
  @Query(value = "UPDATE ACCT_ITEM SET STATE = :state, SETTLE_CHARGE = :settleCharge, STATE_DATE = SYSDATE WHERE ACCT_ITEM_ID = :acctItemId", nativeQuery = true)
  void acctItemBatchUpdate (@Param("state") String state, @Param("settleCharge") Long settleCharge, @Param("acctItemId") Long acctItemId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE ACCT_ITEM A SET A.STATE = :state, A.STATE_DATE = SYSDATE, A.SETTLE_CHARGE = ( SELECT A.SETTLE_CHARGE + B.SETTLE_CHARGE FROM PAYMENT_SETT_ITEM B WHERE B.PAYMENT_SETT_ID = :paymentSettId AND A.ACCT_ITEM_ID = B.ACCT_ITEM_ID ) WHERE EXISTS ( SELECT 1 FROM PAYMENT_SETT_ITEM WHERE PAYMENT_SETT_ID = :paymentSettId AND ACCT_ITEM_ID = A.ACCT_ITEM_ID )", nativeQuery = true)
  void reverseAcctItemStateAndSettleCharge (@Param("state") String state, @Param("paymentSettId") Long paymentSettId);

  @Modifying
  @Query(value = "INSERT INTO ACCT_ITEM ( ACCT_ITEM_ID, BILLING_CYCLE_ID, ACCT_ID, SUBS_ID, ACCT_ITEM_TYPE_ID, CHARGE, BASIC_CHARGE, CREATED_DATE, STATE, STATE_DATE, BAL_ID, SETTLE_CHARGE, SP_ID, ACCT_BOOK_ID ) VALUES ( :acctItemId, :billingCycleId, :acctId, :subsId, :acctItemTypeId, :charge, :basicCharge, :createdDate, :state, :stateDate, :balId, :settleCharge, :spId, :acctBookId )", nativeQuery = true)
  void acctItemBatchStore (@Param("acctItemId") Long acctItemId, @Param("billingCycleId") Long billingCycleId, @Param("acctId") Long acctId, @Param("subsId") Long subsId, @Param("acctItemTypeId") Long acctItemTypeId, @Param("charge") Long charge, @Param("basicCharge") Long basicCharge, @Param("createdDate") LocalDateTime createdDate, @Param("state") String state, @Param("stateDate") LocalDateTime stateDate, @Param("balId") Long balId, @Param("settleCharge") Long settleCharge, @Param("spId") Long spId, @Param("acctBookId") Long acctBookId);

  @Transactional
  @Modifying
  @Query(value = "UPDATE ACCT_ITEM SET STATE_DATE = SYSDATE, STATE = :state WHERE ACCT_ITEM_ID IN ( SELECT NVL(ACCT_ITEM_ID, -1) FROM DISPUTE_ITEM WHERE DISPUTE_ID = :disputeId )", nativeQuery = true)
  void updateDisputeAcctItemState (@Param("disputeId") Long disputeId, @Param("state") String state);

  @Query(value = " SELECT SUM(A.CHARGE + NVL(A.SETTLE_CHARGE, 0)) FROM ACCT_ITEM A WHERE A.ACCT_ID = :acctId AND A.ACCT_ITEM_TYPE_ID = :acctItemTypeId AND A.STATE = 'C' ", nativeQuery = true)
  Long selectOverdueAmount(@Param("acctId") Long acctId, @Param("acctItemTypeId") Long acctItemTypeId);

}
