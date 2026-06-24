package com.sts.sinorita.repository;

import com.sts.sinorita.entity.ReAbInst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ReAbInstRepository extends JpaRepository<ReAbInst, Long> {
  @Transactional
  @Modifying
  @Query(value = """
    INSERT INTO RE_AB_INST (
        EVENT_INST_ID,
        ACCT_BOOK_ID,
        SP_ID
    ) VALUES (
        :eventInstId,
        :acctBookId,
        :spId
    )
    """, nativeQuery = true)
  void reAbInstBatchStore (
    @Param("eventInstId") Long eventInstId,
    @Param("acctBookId") Long acctBookId,
    @Param("spId") Long spId
  );

  @Query(
    value = """
      SELECT T.EVENT_INST_ID
      FROM RE_AB_INST T
      WHERE T.ACCT_BOOK_ID = :paymentId
      """,
    nativeQuery = true
  )
  Optional<Long> selectEventInstIdByPaymentId (@Param("paymentId") Long paymentId);

}
