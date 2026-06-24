package com.sts.sinorita.repository;

import com.sts.sinorita.entity.OriNote;
import com.sts.sinorita.projection.balanceAdjustment.QryOriNoteProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OriNoteRepository extends JpaRepository<OriNote, Long> {
  @Query(value = """
    SELECT
      NOTE_ID                AS noteId,
      PAYMENT_METHOD_ID      AS paymentMethodId,
      BANK_ID                AS bankId,
      VOUCHER                AS voucher,
      CHARGE                 AS charge,
      CHECK_NBR              AS checkNbr,
      CHECK_OWNER_NAME       AS checkOwnerName,
      CHECK_ISSUE_DATE       AS checkIssueDate,
      CHECK_EXP_DATE         AS checkExpDate,
      SCRATCH_CARD_PWD       AS scratchCardPwd,
      CUST_ID                AS custId,
      ACCT_ID                AS acctId
    FROM ORI_NOTE
    WHERE NOTE_ID = :noteId
    """, nativeQuery = true)
  Optional<QryOriNoteProjection> qryOriNote (@Param("noteId") Long noteId);
}

