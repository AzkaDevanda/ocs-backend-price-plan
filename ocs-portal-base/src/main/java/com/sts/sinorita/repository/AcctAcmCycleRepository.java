package com.sts.sinorita.repository;

import com.sts.sinorita.entity.embeddable.AcctAcmCycleId;
import com.sts.sinorita.entity.mdbtt.AcctAcmCycle;
import com.sts.sinorita.projection.balanceAdjustment.AcmValueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AcctAcmCycleRepository extends JpaRepository<AcctAcmCycle, AcctAcmCycleId> {

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO ACCT_ACM_CYCLE (ACCT_ID, RESOURCE_ID, BILLING_CYCLE_ID, VALUE) VALUES (:acctId, :resourceId, :billingCycleId, :value)", nativeQuery = true)
  void insertAcctAcmCycle (Long acctId, Long resourceId, Long billingCycleId, Long value);

  @Transactional
  @Modifying
  @Query(value = "UPDATE ACCT_ACM_CYCLE SET VALUE = NVL(VALUE,0) + :increaseValue WHERE ACCT_ID = :acctId AND RESOURCE_ID = :resourceId AND BILLING_CYCLE_ID = :billingCycleId", nativeQuery = true)
  int updateAcctAcmCycle (@Param("increaseValue") Long increaseValue, @Param("acctId") Long acctId, @Param("resourceId") Long resourceId, @Param("billingCycleId") Long billingCycleId);

  @Query(value = "SELECT VALUE FROM ACCT_ACM_CYCLE WHERE ACCT_ID = :acctId AND RESOURCE_ID = :resourceId AND BILLING_CYCLE_ID = :billingCycleId", nativeQuery = true)
  Optional<AcmValueProjection> getAcmType3 (@Param("acctId") Long acctId, @Param("resourceId") Long resourceId, @Param("billingCycleId") Long billingCycleId);

}
