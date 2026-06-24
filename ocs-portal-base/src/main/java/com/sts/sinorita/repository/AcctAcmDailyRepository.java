package com.sts.sinorita.repository;

import com.sts.sinorita.entity.embeddable.AcctAcmDailyId;
import com.sts.sinorita.entity.mdbtt.AcctAcmDaily;
import com.sts.sinorita.projection.balanceAdjustment.AcmValueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AcctAcmDailyRepository extends JpaRepository<AcctAcmDaily, AcctAcmDailyId> {

  // ========== TYPE 8 ==========
  @Modifying
  @Transactional
  @Query(value = "INSERT INTO ACCT_ACM_DAILY (ACCT_ID, RESOURCE_ID, DATE_STAMP, VALUE) VALUES (:acctId, :resourceId, :dateStamp, :value)", nativeQuery = true)
  void insertAcctAcmDaily (Long acctId, Long resourceId, Long dateStamp, Long value);

  @Transactional
  @Modifying
  @Query(value = "UPDATE ACCT_ACM_DAILY SET VALUE = NVL(VALUE,0) + :increaseValue WHERE ACCT_ID = :acctId AND RESOURCE_ID = :resourceId AND DATE_STAMP = :dateStamp", nativeQuery = true)
  int updateAcctAcmDaily (@Param("increaseValue") Long increaseValue, @Param("acctId") Long acctId, @Param("resourceId") Long resourceId, @Param("dateStamp") Long dateStamp);

  // 8 = ACCT_ACM_DAILY
  @Query(value = "SELECT VALUE FROM ACCT_ACM_DAILY WHERE ACCT_ID = :acctId AND RESOURCE_ID = :resourceId AND DATE_STAMP = :dateStamp", nativeQuery = true)
  Optional<AcmValueProjection> getAcmType8 (@Param("acctId") Long acctId, @Param("resourceId") Long resourceId, @Param("dateStamp") Long dateStamp);

}
