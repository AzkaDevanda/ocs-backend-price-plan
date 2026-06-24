package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BatchAcctBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchAcctBookRepository extends JpaRepository<BatchAcctBook, Long> {

  @Modifying
  @Query(value = "INSERT INTO BATCH_ACCT_BOOK " +
    "(AB_BATCH_ID, ACCT_BOOK_ID, SP_ID) " +
    "VALUES (:abBatchId, :acctBookId, :spId)",
    nativeQuery = true)
  int batchAcctBookStore (
    @Param("abBatchId") Long abBatchId,
    @Param("acctBookId") Long acctBookId,
    @Param("spId") Long spId
  );
}
