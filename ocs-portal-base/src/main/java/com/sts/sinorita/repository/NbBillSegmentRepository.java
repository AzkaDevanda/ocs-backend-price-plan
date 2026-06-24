package com.sts.sinorita.repository;

import com.sts.sinorita.entity.NbBillSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NbBillSegmentRepository extends JpaRepository<NbBillSegment, Long> {
  @Transactional
  @Modifying
  @Query(value = "UPDATE NB_BILL_SEGMENT SET SETT_CHARGE = NVL(SETT_CHARGE, 0) + :SETT_CHARGE WHERE ACCT_ID = :ACCT_ID AND BILLING_CYCLE_ID = :BILLING_CYCLE_ID AND BATCH_ID = :BATCH_ID", nativeQuery = true)
  int billSegment4SettleUpdate (@Param("settCharge") Long settCharge, @Param("acctId") Long acctId, @Param("billingCycleId") Long billingCycleId, @Param("batchId") Long batchId);
}
