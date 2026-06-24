package com.sts.sinorita.repository;

import com.sts.sinorita.entity.PaymentSettItem;
import com.sts.sinorita.entity.PaymentSettItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PaymentSettItemRepository extends JpaRepository<PaymentSettItem, PaymentSettItemId> {
  @Transactional
  @Modifying
  @Query(value = """
    INSERT INTO PAYMENT_SETT_ITEM(
        PAYMENT_SETT_ID,
        ACCT_ITEM_ID,
        SP_ID,
        SETTLE_CHARGE,
        BILL_ID
    )
    VALUES(
        :paymentSettId,
        :acctItemId,
        :spId,
        :settleCharge,
        :billId
    )
    """, nativeQuery = true)
  void paymentSettItemBatchStore (
    @Param("paymentSettId") Long paymentSettId,
    @Param("acctItemId") Long acctItemId,
    @Param("spId") Long spId,
    @Param("settleCharge") Long settleCharge,
    @Param("billId") Long billId
  );


}
