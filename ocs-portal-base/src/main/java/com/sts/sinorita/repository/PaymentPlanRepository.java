package com.sts.sinorita.repository;

import com.sts.sinorita.entity.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long> {
  @Transactional
  @Modifying
  @Query(value = "UPDATE PAYMENT_PLAN SET SETT_CHARGE = NVL(SETT_CHARGE, 0) + :SETT_CHARGE WHERE BILL_ID IN ( SELECT BILL_ID FROM NB_BILL_SEGMENT WHERE ACCT_ID = :ACCT_ID AND BILLING_CYCLE_ID = :BILLING_CYCLE_ID AND BATCH_ID = :BATCH_ID )", nativeQuery = true)
  void updatePaymentPlan (@Param("SETT_CHARGE") Long settCharge, @Param("ACCT_ID") Long acctId, @Param("BILLING_CYCLE_ID") Long billingCycleId, @Param("BATCH_ID") Long batchId);

  @Transactional
  @Modifying
  @Query(value = "UPDATE PAYMENT_PLAN SET SETT_CHARGE = NVL(SETT_CHARGE, 0) + :SETT_CHARGE, SETT_TAX_CHARGE = NVL(SETT_TAX_CHARGE, 0) + :SETT_TAX_CHARGE, SETT_DATE = SYSDATE WHERE BILL_ID = :BILL_ID AND PLAN_STATUS = 'A' AND (:SP_ID IS NULL OR NVL(SP_ID,0) = :SP_ID)", nativeQuery = true)
  void updatePaymentPlanSettChargeByBillId (@Param("BILL_ID") Long billId, @Param("SETT_CHARGE") Long settCharge, @Param("SETT_TAX_CHARGE") Long settTaxCharge, @Param("SP_ID") Long spId);

  @Transactional
  @Modifying
  @Query(value = "UPDATE PAYMENT_PLAN SET SETT_CHARGE = NVL(SETT_CHARGE,0) + :SETT_CHARGE, SETT_TAX_CHARGE = NVL(SETT_TAX_CHARGE,0) + :SETT_TAX_CHARGE, SETT_DATE = SYSDATE WHERE PAYMENT_PLAN_ID = :PAYMENT_PLAN_ID AND PLAN_STATUS = 'A' AND (:SP_ID IS NULL OR NVL(SP_ID,0) = :SP_ID)", nativeQuery = true)
  void updatePaymentPlanSettChargeById (@Param("PAYMENT_PLAN_ID") Long paymentPlanId, @Param("SETT_CHARGE") Long settCharge, @Param("SETT_TAX_CHARGE") Long settTaxCharge, @Param("SP_ID") Long spId);

  @Transactional
  @Modifying
  @Query(value = "UPDATE INSTALLMENT_ITEM SET SETT_CHARGE = NVL(SETT_CHARGE,0) + :SETT_CHARGE WHERE PAYMENT_PLAN_ID = :PAYMENT_PLAN_ID AND (:SP_ID IS NULL OR NVL(SP_ID,0) = :SP_ID)", nativeQuery = true)
  void updateInstallmentItemSettChargeByPaymentPlanId (@Param("PAYMENT_PLAN_ID") Long paymentPlanId, @Param("SETT_CHARGE") Long settCharge, @Param("SP_ID") Long spId);


}
