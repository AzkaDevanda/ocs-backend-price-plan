package com.sts.sinorita.repository;

import com.sts.sinorita.entity.embeddable.AcctAcmId;
import com.sts.sinorita.entity.mdbtt.AcctAcm;
import com.sts.sinorita.projection.balanceAdjustment.AcmValueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AcctAcmRepository extends JpaRepository<AcctAcm, AcctAcmId> {
  @Modifying
  @Transactional
  @Query(value = "INSERT INTO ACCT_ACM (ACCT_ID, RESOURCE_ID, VALUE) VALUES (:acctId, :resourceId, :value)", nativeQuery = true)
  void insertAcctAcm (Long acctId, Long resourceId, Long value);

  @Transactional
  @Modifying
  @Query(value = "UPDATE ACCT_ACM SET VALUE = NVL(VALUE,0) + :increaseValue WHERE ACCT_ID = :acctId AND RESOURCE_ID = :resourceId", nativeQuery = true)
  int updateAcctAcm (@Param("increaseValue") Long increaseValue, @Param("acctId") Long acctId, @Param("resourceId") Long resourceId);

  @Query(value = "SELECT VALUE FROM ACCT_ACM_CYCLE WHERE ACCT_ID = :acctId AND RESOURCE_ID = :resourceId AND BILLING_CYCLE_ID = :billingCycleIdSELECT VALUE FROM ACCT_ACM WHERE ACCT_ID = :acctId AND RESOURCE_ID = :resourceId", nativeQuery = true)
  Optional<AcmValueProjection> getAcmType4 (@Param("acctId") Long acctId, @Param("resourceId") Long resourceId);

}
