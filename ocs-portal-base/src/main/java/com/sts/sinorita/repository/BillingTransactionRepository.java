package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BillingTransaction;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingTransactionRepository extends JpaRepository<BillingTransaction, Long> {

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO BILLING_TRANSACTION ( TRANSACTION_ID, SEQ, ACCT_ID, SUBS_ID, SUBS_EVENT_ID, ACCT_BOOK_TYPE, PAYMENT_METHOD_ID, LOAN_TYPE, DEPOSIT_TYPE_ID, ACCT_RES_ID, REASON_ID, REVERSED_FLAG, OFFER_ID, PARTNER, PARTNER_SN, COMMENTS, REF_ATTR, REVERSED_ID, PARTY_TYPE, PARTY_CODE, CONTACT_CHANNEL_ID, EVENT_PAYMENT_ID, EVENT_INST_ID, DEBIT_ITEM_ID, ACCT_BOOK_ID, ORDER_ITEM_ID, DEPOSIT_ITEM_ID, CHARGE1, GL_CODE1, GL_DIRECTION1, CREATED_DATE, PARTNER_DATE, SP_ID ) VALUES ( :transactionId, :seq, :acctId, :subsId, :subsEventId, :acctBookType, :paymentMethodId, :loanType, :depositTypeId, :acctResId, :reasonId, :reversedFlag, :offerId, :partner, :partnerSn, :comments, :refAttr, :reversedId, :partyType, :partyCode, :contactChannelId, :eventPaymentId, :eventInstId, :debitItemId, :acctBookId, :orderItemId, :depositItemId, :charge1, :glCode1, :glDirection1, :createdDate, :partnerDate, :spId )", nativeQuery = true)
  void insertBillingTransaction(@Param("transactionId") Long transactionId, @Param("seq") Long seq,
      @Param("acctId") Long acctId, @Param("subsId") Long subsId, @Param("subsEventId") Long subsEventId,
      @Param("acctBookType") String acctBookType, @Param("paymentMethodId") Long paymentMethodId,
      @Param("loanType") String loanType, @Param("depositTypeId") Long depositTypeId,
      @Param("acctResId") Long acctResId, @Param("reasonId") Long reasonId, @Param("reversedFlag") String reversedFlag,
      @Param("offerId") Long offerId, @Param("partner") String partner, @Param("partnerSn") String partnerSn,
      @Param("comments") String comments, @Param("refAttr") String refAttr, @Param("reversedId") Long reversedId,
      @Param("partyType") String partyType, @Param("partyCode") String partyCode,
      @Param("contactChannelId") Long contactChannelId, @Param("eventPaymentId") Long eventPaymentId,
      @Param("eventInstId") Long eventInstId, @Param("debitItemId") Long debitItemId,
      @Param("acctBookId") Long acctBookId, @Param("orderItemId") Long orderItemId,
      @Param("depositItemId") Long depositItemId, @Param("charge1") Long charge1, @Param("glCode1") String glCode1,
      @Param("glDirection1") String glDirection1, @Param("createdDate") LocalDateTime createdDate,
      @Param("partnerDate") LocalDateTime partnerDate, @Param("spId") Integer spId);

}
