package com.sts.sinorita.repository;

import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookData;
import com.sts.sinorita.entity.AcctBook;
import com.sts.sinorita.projection.balanceAdjustment.FindAcctBookByPartnerSnProjection;
import com.sts.sinorita.projection.balanceAdjustment.QryRechargePaymentInfoProjection;
import com.sts.sinorita.projection.balanceAdjustment.SelectLastRecentValidAcctBookProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AcctBookRepository extends JpaRepository<AcctBook, Long> {
  @Transactional
  @Modifying
  @Query(value = "INSERT INTO ACCT_BOOK( ACCT_BOOK_ID, ACCT_ID, ACCT_RES_ID, ACCT_BOOK_TYPE, CREATED_DATE, PRE_BALANCE, PRE_EXP_DATE, CHARGE, BILL_ID, PARTY_TYPE, PARTY_CODE, PRE_SUTTLE_BAL, SECONDS, BAL_ID, CONTACT_CHANNEL_ID, EVENT_PAYMENT_ID, SP_ID, PRE_EFF_DATE, EFF_SECONDS, REF_ATTR, ORG_ID ) VALUES ( :acctBookId, :acctId, :acctResId, :acctBookType, :createdDate, :preBalance, :preExpDate, :charge, :billId, :partyType, :partyCode, :preSuttleBal, :seconds, :balId, :contactChannelId, :eventPaymentId, :spId, :preEffDate, :effSeconds, :refAttr, :orgId )", nativeQuery = true)
  void acctBookDataStore(@Param("acctBookId") Long acctBookId, @Param("acctId") Long acctId,
      @Param("acctResId") Long acctResId, @Param("acctBookType") String acctBookType,
      @Param("createdDate") LocalDateTime createdDate, @Param("preBalance") Long preBalance,
      @Param("preExpDate") LocalDateTime preExpDate, @Param("charge") Long charge, @Param("billId") Long billId,
      @Param("partyType") String partyType, @Param("partyCode") String partyCode,
      @Param("preSuttleBal") Long preSuttleBal, @Param("seconds") Long seconds, @Param("balId") Long balId,
      @Param("contactChannelId") Long contactChannelId, @Param("eventPaymentId") Long eventPaymentId,
      @Param("spId") Long spId, @Param("preEffDate") LocalDateTime preEffDate, @Param("effSeconds") Long effSeconds,
      @Param("refAttr") String refAttr, @Param("orgId") Long orgId);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO ACCT_BOOK( ACCT_BOOK_ID, ACCT_ID, ACCT_RES_ID, ACCT_BOOK_TYPE, CREATED_DATE, PRE_BALANCE, PRE_EXP_DATE, CHARGE, BILL_ID, PARTY_TYPE, PARTY_CODE, PRE_SUTTLE_BAL, SECONDS, BAL_ID, CONTACT_CHANNEL_ID, EVENT_PAYMENT_ID, SP_ID, PRE_EFF_DATE, EFF_SECONDS, REF_ATTR, EVENT_INST_ID ) VALUES ( :acctBookId, :acctId, :acctResId, :acctBookType, :createdDate, :preBalance, :preExpDate, :charge, :billId, :partyType, :partyCode, :preSuttleBal, :seconds, :balId, :contactChannelId, :eventPaymentId, :spId, :preEffDate, :effSeconds, :refAttr, :eventInstId )", nativeQuery = true)
  void acctBookDtoBatchStore(@Param("acctBookId") Long acctBookId, @Param("acctId") Long acctId,
      @Param("acctResId") Long acctResId, @Param("acctBookType") String acctBookType,
      @Param("createdDate") LocalDateTime createdDate, @Param("preBalance") Long preBalance,
      @Param("preExpDate") LocalDateTime preExpDate, @Param("charge") Long charge, @Param("billId") Long billId,
      @Param("partyType") String partyType, @Param("partyCode") String partyCode,
      @Param("preSuttleBal") Long preSuttleBal, @Param("seconds") Long seconds, @Param("balId") Long balId,
      @Param("contactChannelId") Long contactChannelId, @Param("eventPaymentId") Long eventPaymentId,
      @Param("spId") Long spId, @Param("preEffDate") LocalDateTime preEffDate, @Param("effSeconds") Long effSeconds,
      @Param("refAttr") String refAttr, @Param("eventInstId") Long eventInstId);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO ACCT_BOOK( ACCT_BOOK_ID, ACCT_ID, ACCT_RES_ID, ACCT_BOOK_TYPE, CREATED_DATE, PRE_BALANCE, PRE_EXP_DATE, CHARGE, BILL_ID, PARTY_TYPE, PARTY_CODE, PRE_SUTTLE_BAL, SECONDS, BAL_ID, CONTACT_CHANNEL_ID, EVENT_PAYMENT_ID, SP_ID, PRE_EFF_DATE, EFF_SECONDS ) VALUES ( :acctBookId, :acctId, :acctResId, :acctBookType, :createdDate, :preBalance, :preExpDate, :charge, :billId, :partyType, :partyCode, :preSuttleBal, :seconds, :balId, :contactChannelId, :eventPaymentId, :spId, :preEffDate, :effSeconds )", nativeQuery = true)
  void insertAcctBookDataBatch(@Param("acctBookId") Long acctBookId, @Param("acctId") Long acctId,
      @Param("acctResId") Long acctResId, @Param("acctBookType") String acctBookType,
      @Param("createdDate") LocalDateTime createdDate, @Param("preBalance") Long preBalance,
      @Param("preExpDate") LocalDateTime preExpDate, @Param("charge") Long charge, @Param("billId") Long billId,
      @Param("partyType") String partyType, @Param("partyCode") String partyCode,
      @Param("preSuttleBal") Long preSuttleBal, @Param("seconds") Long seconds, @Param("balId") Long balId,
      @Param("contactChannelId") Long contactChannelId, @Param("eventPaymentId") Long eventPaymentId,
      @Param("spId") Long spId, @Param("preEffDate") LocalDateTime preEffDate, @Param("effSeconds") Long effSeconds);

  @Query(value = "SELECT B.PAYMENT_ID AS tradeSn, B.PAYMENT_DATE AS tradeTime, DECODE( B.SCRATCH_CARD_PWD, NULL, DECODE(A.PARTY_CODE, 'PRE_CHARGE', 'I', 'C'), 'V' ) AS tradeMethod, 'C' AS tradeType, C.ACCT_NBR AS accountCode, B.SCRATCH_CARD_PWD AS vcPin, B.SUBMIT_AMOUNT AS amount, TRUNC(A.SECONDS / 86400) AS extendDays FROM ACCT_BOOK A JOIN PAYMENT B ON A.ACCT_BOOK_ID = B.PAYMENT_ID JOIN ACCT C ON A.ACCT_ID = C.ACCT_ID WHERE A.ACCT_BOOK_ID = :acctBookId", nativeQuery = true)
  Optional<QryRechargePaymentInfoProjection> qryRechargePaymentInfo(@Param("acctBookId") Long acctBookId);

  @Query(value = "SELECT * FROM ( SELECT A.ACCT_BOOK_ID AS acctBookId, A.ACCT_ID AS acctId, A.ACCT_RES_ID AS acctResId, A.ACCT_BOOK_TYPE AS acctBookType, A.CREATED_DATE AS createdDate, A.PRE_BALANCE AS preBalance, A.PRE_EXP_DATE AS preExpDate, A.CHARGE AS charge, A.BILL_ID AS billId, A.PARTY_TYPE AS partyType, A.PARTY_CODE AS partyCode, A.PRE_SUTTLE_BAL AS preSuttleBal, A.SECONDS AS seconds, A.BAL_ID AS balId, A.CONTACT_CHANNEL_ID AS contactChannelId, A.EVENT_PAYMENT_ID AS eventPaymentId, A.SP_ID AS spId FROM ACCT_BOOK A JOIN PAYMENT B ON A.ACCT_BOOK_ID = B.PAYMENT_ID WHERE A.ACCT_ID = :acctId AND A.ACCT_BOOK_TYPE = :acctBookType AND B.REVERSED_PAYMENT_ID IS NULL AND NOT EXISTS ( SELECT 1 FROM PAYMENT D WHERE D.REVERSED_PAYMENT_ID = B.PAYMENT_ID ) AND A.ACCT_BOOK_ID < :beforeAcctBookId ORDER BY A.ACCT_BOOK_ID DESC ) WHERE ROWNUM = 1", nativeQuery = true)
  Optional<SelectLastRecentValidAcctBookProjection> selectLastRecentValidAcctBook(
      @Param("acctBookType") String acctBookType, @Param("acctId") Long acctId,
      @Param("beforeAcctBookId") Long beforeAcctBookId);

  @Query(value = "SELECT A.ACCT_BOOK_ID, A.ACCT_ID, A.ACCT_RES_ID, A.ACCT_BOOK_TYPE, A.CREATED_DATE, A.PRE_BALANCE, A.PRE_EXP_DATE, A.CHARGE, A.BILL_ID, A.PARTY_TYPE, A.PARTY_CODE, A.PRE_SUTTLE_BAL, A.SECONDS, A.BAL_ID, A.CONTACT_CHANNEL_ID, A.SP_ID FROM ACCT_BOOK A JOIN AB_EVENT_BENEFIT B ON A.ACCT_BOOK_ID = B.ACCT_BOOK_ID WHERE B.EVENT_INST_ID = :eventInstId", nativeQuery = true)
  List<AcctBookData> selectBenefitAcctBookList(@Param("eventInstId") Long eventInstId);

  @Query(value = "SELECT A.ACCT_BOOK_ID, A.ACCT_ID, A.ACCT_RES_ID, A.ACCT_BOOK_TYPE, A.CREATED_DATE, A.PRE_BALANCE, A.PRE_EXP_DATE, A.CHARGE, A.BILL_ID, A.PARTY_TYPE, A.PARTY_CODE, A.PRE_SUTTLE_BAL, A.SECONDS, A.BAL_ID, A.CONTACT_CHANNEL_ID, A.SP_ID FROM ACCT_BOOK A JOIN EVENT_SETT B ON A.ACCT_BOOK_ID = B.ACCT_BOOK_ID JOIN ACCT_BOOK C ON B.EVENT_PAYMENT_ID = C.EVENT_PAYMENT_ID WHERE C.ACCT_BOOK_ID = :acctBookId", nativeQuery = true)
  List<AcctBookData> selectOneOffFeeAcctBookList(@Param("acctBookId") Long acctBookId);

  @Query(value = "SELECT A.ACCT_BOOK_ID AS acctBookId, A.ACCT_ID AS acctId, A.ACCT_RES_ID AS acctResId, A.ACCT_BOOK_TYPE AS acctBookType, A.CREATED_DATE AS createdDate, A.PRE_BALANCE AS preBalance, A.PRE_EXP_DATE AS preExpDate, A.CHARGE AS charge, A.BILL_ID AS billId, A.PARTY_TYPE AS partyType, A.PARTY_CODE AS partyCode, A.PRE_SUTTLE_BAL AS preSuttleBal, A.SECONDS AS seconds, A.BAL_ID AS balId, A.CONTACT_CHANNEL_ID AS contactChannelId, A.SP_ID AS spId FROM ACCT_BOOK A JOIN ACCT_BOOK_RECONCILE B ON A.ACCT_BOOK_ID = B.ACCT_BOOK_ID WHERE B.PARTNER_SN = :partnerSn AND (:contactChannelId IS NULL OR A.CONTACT_CHANNEL_ID = :contactChannelId) ORDER BY A.ACCT_BOOK_ID DESC", nativeQuery = true)
  List<FindAcctBookByPartnerSnProjection> findAcctBookByPartnerSn(
      @Param("partnerSn") String partnerSn,
      @Param("contactChannelId") Long contactChannelId);
}
