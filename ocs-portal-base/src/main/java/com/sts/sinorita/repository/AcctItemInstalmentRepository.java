package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcctItemInstalment;
import com.sts.sinorita.entity.AcctItemInstalmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface AcctItemInstalmentRepository extends JpaRepository<AcctItemInstalment, AcctItemInstalmentId> {
  @Transactional
  @Modifying
  @Query(value = "INSERT INTO ACCT_ITEM_INSTALMENT ( INSTLMENT_ID, SEQ, ACCT_ID, SUBS_ID, CHARGE, ACCT_ITEM_ID, BILLING_CYCLE_ID, ACCT_ITEM_TYPE_ID, STATE, STATE_DATE ) VALUES ( :instalmentId, :seq, :acctId, :subsId, :charge, :acctItemId, :billingCycleId, :acctItemTypeId, :state, :stateDate )", nativeQuery = true)
  void insertAcctItemInstalment (@Param("instalmentId") Long instalmentId, @Param("seq") Long seq, @Param("acctId") Long acctId, @Param("subsId") Long subsId, @Param("charge") Long charge, @Param("acctItemId") Long acctItemId, @Param("billingCycleId") Long billingCycleId, @Param("acctItemTypeId") Long acctItemTypeId, @Param("state") String state, @Param("stateDate") LocalDateTime stateDate);
}
