package com.sts.sinorita.repository;

import com.sts.sinorita.entity.DebitPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DebitPaymentRepository extends JpaRepository<DebitPayment, Long> {
  @Transactional
  @Modifying
  @Query(value = "INSERT INTO DEBIT_PAYMENT( ACCT_BOOK_ID, DEBIT_ITEM_ID, SP_ID, REF_ATTR ) VALUES( :ACCT_BOOK_ID, :DEBIT_ITEM_ID, :SP_ID, :REF_ATTR )", nativeQuery = true)
  void insertDebitPayment (@Param("ACCT_BOOK_ID") Long acctBookId, @Param("DEBIT_ITEM_ID") Long debitItemId, @Param("SP_ID") Long spId, @Param("REF_ATTR") String refAttr);

}
