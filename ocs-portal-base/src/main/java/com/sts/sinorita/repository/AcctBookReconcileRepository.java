package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcctBookReconcile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface AcctBookReconcileRepository extends JpaRepository<AcctBookReconcile, Long> {
  @Transactional
  @Modifying
  @Query(value = "INSERT INTO ACCT_BOOK_RECONCILE ( ACCT_BOOK_ID, RECONCILE_STATE, LOG_ID, CONTACT_CHANNEL_ID, PARTNER_SN, SP_ID, ACCT_BOOK_TYPE, CHARGE, CREATED_DATE, PAYMENT_DATE, PARTNER_DETAIL, TRANSACTION_TYPE ) VALUES ( :acctBookId, :reconcileState, :logId, :contactChannelId, :partnerSn, :spId, :acctBookType, :charge, :createdDate, :paymentDate, :partnerDetail, :transactionType )", nativeQuery = true)
  void acctBookReconcileStore(@Param("acctBookId") Long acctBookId, @Param("reconcileState") String reconcileState,
      @Param("logId") Long logId, @Param("contactChannelId") Long contactChannelId,
      @Param("partnerSn") String partnerSn, @Param("spId") Long spId, @Param("acctBookType") String acctBookType,
      @Param("charge") Long charge, @Param("createdDate") LocalDateTime createdDate,
      @Param("paymentDate") LocalDateTime paymentDate, @Param("partnerDetail") String partnerDetail,
      @Param("transactionType") String transactionType);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO ACCT_BOOK_RECONCILE ( ACCT_BOOK_ID, RECONCILE_STATE, LOG_ID, CONTACT_CHANNEL_ID, PARTNER_SN, SP_ID, ACCT_BOOK_TYPE, CHARGE, CREATED_DATE, PAYMENT_DATE, PARTNER_DETAIL, TRANSACTION_TYPE ) VALUES ( :acctBookId, :reconcileState, :logId, :contactChannelId, :partnerSn, :spId, :acctBookType, :charge, :createdDate, :paymentDate, :partnerDetail, :transactionType )", nativeQuery = true)
  void insertAcctBookReconcile(@Param("acctBookId") Long acctBookId, @Param("reconcileState") String reconcileState,
      @Param("logId") Long logId, @Param("contactChannelId") Long contactChannelId,
      @Param("partnerSn") String partnerSn, @Param("spId") Long spId, @Param("acctBookType") String acctBookType,
      @Param("charge") Long charge, @Param("createdDate") LocalDateTime createdDate,
      @Param("paymentDate") LocalDateTime paymentDate, @Param("partnerDetail") String partnerDetail,
      @Param("transactionType") String transactionType);

  @Query(value = "SELECT B.ACCT_BOOK_ID FROM ACCT_BOOK_RECONCILE B WHERE B.PARTNER_SN = :partnerSn AND (:contactChannelId IS NULL OR B.CONTACT_CHANNEL_ID = :contactChannelId)", nativeQuery = true)
  Long selectAcctBookIdByPartnerSn(
      @Param("partnerSn") String partnerSn,
      @Param("contactChannelId") Long contactChannelId);
}
