package com.sts.sinorita.repository;

import com.sts.sinorita.dto.request.balanceAdjustment.BillEx;
import com.sts.sinorita.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Long> {
  @Query(value = """
    SELECT BILL_ID, BILL_NBR, BILLING_CYCLE_ID, ACCT_ID, PRE_BALANCE, DUE, DISPUTE_CHARGE, RECV_CHARGE, PAST_ADJUST_CHARGE, ADJUST_CHARGE, CHARGE_BE_ADJUSTED, SP_ID, BILL_CODE, SETT_CHARGE, CHARGE_BE_REVERSED FROM BILL WHERE BILL_ID = :billId
    """, nativeQuery = true)
  BillEntity selectBillByBillId (@Param("billId") Long billId);

  @Query(value = """
    SELECT BILL_ID, BILL_NBR, BILLING_CYCLE_ID, ACCT_ID, PRE_BALANCE, DUE, DISPUTE_CHARGE, RECV_CHARGE, PAST_ADJUST_CHARGE, ADJUST_CHARGE, CHARGE_BE_ADJUSTED, SP_ID, BILL_CODE, SETT_CHARGE, CHARGE_BE_REVERSED FROM BILL WHERE BILL_NBR = :billNbr
    """, nativeQuery = true)
  BillEntity selectBillByBillNbr (@Param("billNbr") String billNbr);

  @Query(value = """
    SELECT T.BILL_ID, T.BILL_NBR, T.ACCT_ID, T.BILLING_CYCLE_ID, NVL(T.PRE_BALANCE,0) AS PRE_BALANCE, NVL(T.DUE,0) AS DUE, NVL(T.ADJUST_CHARGE,0) AS ADJUST_CHARGE, NVL(T.DISPUTE_CHARGE,0) AS DISPUTE_CHARGE, (NVL(T.RECV_CHARGE,0) + NVL(T.CHARGE_BE_REVERSED,0)) AS RECV_CHARGE, NVL(T.SETT_CHARGE,0) AS SETT_CHARGE FROM BILL T WHERE T.ACCT_ID = :acctId AND T.BILLING_CYCLE_ID IN ( SELECT A.BILLING_CYCLE_ID FROM BILLING_CYCLE A, ACCT B WHERE A.CYCLE_BEGIN_DATE <= SYSDATE AND A.CYCLE_END_DATE > SYSDATE AND A.BILLING_CYCLE_TYPE_ID = B.BILLING_CYCLE_TYPE_ID AND B.ACCT_ID = :acctId)
    """, nativeQuery = true)
  BillEntity selectBillByAcctId (@Param("acctId") Long acctId);

  @Query(value = """
    SELECT T.BILL_ID, T.SUBS_EVENT_CHARGE, T.PAY_IN_TIME FROM BILL_EX T WHERE T.BILL_ID = :billId
    """, nativeQuery = true)
  BillEx findBillExByBillId (@Param("billId") Long billId);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO BILL( BILL_ID, BILL_NBR, ACCT_ID, BILLING_CYCLE_ID, PRE_BALANCE, DUE, DISPUTE_CHARGE, RECV_CHARGE, PAST_ADJUST_CHARGE, ADJUST_CHARGE, CHARGE_BE_ADJUSTED, SP_ID, BILL_CODE, CHARGE_BE_REVERSED ) VALUES ( :billId, :billNbr, :acctId, :billingCycleId, :preBalance, :due, :disputeCharge, :recvCharge, :pastAdjustCharge, :adjustCharge, :chargeBeAdjusted, :spId, :billCode, :chargeBeReversed", nativeQuery = true)
  void billStore (@Param("billId") Long billId, @Param("billNbr") String billNbr, @Param("acctId") Long acctId, @Param("billingCycleId") Long billingCycleId, @Param("preBalance") Long preBalance, @Param("due") Long due, @Param("disputeCharge") Long disputeCharge, @Param("recvCharge") Long recvCharge, @Param("pastAdjustCharge") Long pastAdjustCharge, @Param("adjustCharge") Long adjustCharge, @Param("chargeBeAdjusted") Long chargeBeAdjusted, @Param("spId") Long spId, @Param("billCode") String billCode, @Param("chargeBeReversed") Long chargeBeReversed);

  @Transactional
  @Modifying
  @Query(value = "UPDATE BILL SET DISPUTE_CHARGE = :disputeCharge, RECV_CHARGE = :recvCharge, PAST_ADJUST_CHARGE = :pastAdjustCharge, ADJUST_CHARGE = :adjustCharge, SETT_CHARGE = :settCharge, CHARGE_BE_REVERSED = :chargeBeReversed, CHARGE_BE_ADJUSTED = :chargeBeAdjusted WHERE BILL_ID = :billId", nativeQuery = true)
  void billUpdate (@Param("disputeCharge") Long disputeCharge, @Param("recvCharge") Long recvCharge, @Param("pastAdjustCharge") Long pastAdjustCharge, @Param("adjustCharge") Long adjustCharge, @Param("settCharge") Long settCharge, @Param("chargeBeReversed") Long chargeBeReversed, @Param("chargeBeAdjusted") Long chargeBeAdjusted, @Param("billId") Long billId);

  @Transactional
  @Modifying
  @Query(value = "UPDATE BILL SET SETT_CHARGE = NVL(SETT_CHARGE, 0) + :settCharge WHERE ACCT_ID = :acctId AND BILLING_CYCLE_ID = :billingCycleId", nativeQuery = true)
  void bill4SettleUpdate (@Param("settCharge") Long settCharge, @Param("acctId") Long acctId, @Param("billingCycleId") Long billingCycleId);

}
