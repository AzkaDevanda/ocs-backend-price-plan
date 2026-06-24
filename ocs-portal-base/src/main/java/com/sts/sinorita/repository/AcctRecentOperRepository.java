package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcctRecentOper;
import com.sts.sinorita.projection.balanceAdjustment.SelectAcctRecentOperProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository
public interface AcctRecentOperRepository extends JpaRepository<AcctRecentOper, Long> {
  @Query(value = "SELECT ACCT_ID AS acctId, RECENT_OPER AS recentOper, UPDATE_DATE AS updateDate FROM ACCT_RECENT_OPER WHERE ACCT_ID = :acctId", nativeQuery = true)
  Optional<SelectAcctRecentOperProjection> selectAcctRecentOper (@Param("acctId") Long acctId);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO ACCT_RECENT_OPER (ACCT_ID, RECENT_OPER, UPDATE_DATE) VALUES (:acctId, :recentOper, :updateDate)", nativeQuery = true)
  void insertAcctRecentOper (@Param("acctId") Long acctId, @Param("recentOper") String recentOper, @Param("updateDate") LocalDateTime updateDate);

  @Transactional
  @Modifying
  @Query(value = "UPDATE ACCT_RECENT_OPER SET RECENT_OPER = :recentOper, UPDATE_DATE = :updateDate WHERE ACCT_ID = :acctId", nativeQuery = true)
  void updateAcctRecentOper (@Param("recentOper") String recentOper, @Param("updateDate") LocalDateTime updateDate, @Param("acctId") Long acctId);
}
