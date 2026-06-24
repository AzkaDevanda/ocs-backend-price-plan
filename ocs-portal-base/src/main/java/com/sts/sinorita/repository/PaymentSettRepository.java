package com.sts.sinorita.repository;

import com.sts.sinorita.entity.PaymentSett;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface PaymentSettRepository extends JpaRepository<PaymentSett, Long> {
  @Transactional
  @Modifying
  @Query(value = "INSERT INTO PAYMENT_SETT( PAYMENT_SETT_ID, ACCT_BOOK_ID, REVERSED_PAYMENT_SETT_ID, CHARGE, CREATED_DATE, SP_ID, TOLERANCE ) VALUES( :paymentSettId, :acctBookId, :reversedPaymentSettId, :charge, :createdDate, :spId, :tolerance )", nativeQuery = true)
  void paymentSettStore (@Param("paymentSettId") Long paymentSettId, @Param("acctBookId") Long acctBookId, @Param("reversedPaymentSettId") Long reversedPaymentSettId, @Param("charge") Long charge, @Param("createdDate") LocalDateTime createdDate, @Param("spId") Long spId, @Param("tolerance") Long tolerance);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO PAYMENT_SETT( PAYMENT_SETT_ID, ACCT_BOOK_ID, REVERSED_PAYMENT_SETT_ID, CHARGE, CREATED_DATE, SP_ID, TOLERANCE ) VALUES ( :paymentSettId, :acctBookId, :reversedPaymentSettId, :charge, :createdDate, :spId, :tolerance )", nativeQuery = true)
  void insertPaymentSett (@Param("paymentSettId") Long paymentSettId, @Param("acctBookId") Long acctBookId, @Param("reversedPaymentSettId") Long reversedPaymentSettId, @Param("charge") Long charge, @Param("createdDate") LocalDateTime createdDate, @Param("spId") Long spId, @Param("tolerance") Long tolerance);


}
