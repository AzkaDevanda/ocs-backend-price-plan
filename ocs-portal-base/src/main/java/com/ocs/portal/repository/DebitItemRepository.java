package com.ocs.portal.repository;

import com.ocs.portal.entity.DebitItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface DebitItemRepository extends JpaRepository<DebitItem, Long> {
  @Transactional
  @Modifying
  @Query(value = """
        UPDATE DEBIT_ITEM
        SET REVERSED_BY_ITEM_ID = :REVERSED_BY_ITEM_ID
        WHERE DEBIT_ITEM_ID = :DEBIT_ITEM_ID
    """, nativeQuery = true)
  void updateDebitItemReverseRela (
    @Param("REVERSED_BY_ITEM_ID") Long reversedByItemId,
    @Param("DEBIT_ITEM_ID") Long oldDebitItemId
  );

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO DEBIT_ITEM( DEBIT_ITEM_ID, ACCT_ID, CHARGE, CREATED_DATE, RATIO, SP_ID, REVERSED_ITEM_ID, REVERSED_BY_ITEM_ID, ACCT_BOOK_ID, LOAN_TYPE, COMMISSION, VOLUME, PRICE_PLAN_CODE ) VALUES( :DEBIT_ITEM_ID, :ACCT_ID, :CHARGE, :CREATED_DATE, :RATIO, :SP_ID, :REVERSED_ITEM_ID, :REVERSED_BY_ITEM_ID, :ACCT_BOOK_ID, :LOAN_TYPE, :COMMISSION, :VOLUME, :PRICE_PLAN_CODE )", nativeQuery = true)
  void insertDebitItem (@Param("DEBIT_ITEM_ID") Long debitItemId, @Param("ACCT_ID") Long acctId, @Param("CHARGE") Long charge, @Param("CREATED_DATE") LocalDateTime createdDate, @Param("RATIO") Long ratio, @Param("SP_ID") Long spId, @Param("REVERSED_ITEM_ID") Long reversedItemId, @Param("REVERSED_BY_ITEM_ID") Long reversedByItemId, @Param("ACCT_BOOK_ID") Long acctBookId, @Param("LOAN_TYPE") Long loanType, @Param("COMMISSION") Long commission, @Param("VOLUME") Long volume, @Param("PRICE_PLAN_CODE") String pricePlanCode);

}
