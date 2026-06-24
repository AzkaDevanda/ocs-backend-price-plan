package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BillingCycle;
import com.sts.sinorita.projection.accountConfig.BillingCycleLastProjection;
import com.sts.sinorita.projection.acct.BillingCycleListProjection;
import com.sts.sinorita.projection.balanceAdjustment.SelectBillingCycleProjection;
import com.sts.sinorita.projection.balanceAdjustment.SelectCurBillingCycleIDByAcctIdProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BillingCycleRepository extends JpaRepository<BillingCycle, Integer> {
  @Query(value = """
    SELECT A.BILLING_CYCLE_ID AS billingCycleId, A.BILLING_CYCLE_TYPE_ID AS billingCycleTypeId, A.CYCLE_BEGIN_DATE AS cycleBeginDate, A.CYCLE_END_DATE AS cycleEndDate, A.STATE AS state, B.LOOKUP_NAME AS stateFlag, A.DEBT_DATE AS debtDate, A.RUN_DATE AS runDate, A.DOCUMENT_DATE AS documentDate, A.POSTING_DATE AS postingDate, A.INVOICE_DATE AS invoiceDate, A.ORIGIN_DATE AS originDate, A.NOTIFICATION_DATE AS notificationDate FROM BILLING_CYCLE A, DOMAIN B WHERE B.TABLE_NAME = 'BILLING_CYCLE' AND B.COLUMN_NAME = 'STATE' AND B.VALUE = A.STATE AND A.STATE = :state AND A.BILLING_CYCLE_TYPE_ID = :billingCycleTypeId AND NVL(A.SP_ID, 0) = :spId ORDER BY A.CYCLE_BEGIN_DATE DESC
    """, nativeQuery = true)
  Page<BillingCycleListProjection> findBillingCyclelist (@Param("state") Character state, @Param("billingCycleTypeId") Integer billingCycleTypeId, @Param("spId") Integer spId, Pageable pageable);

  @Query(value = "SELECT A.BILLING_CYCLE_ID, A.BILLING_CYCLE_TYPE_ID, A.CYCLE_BEGIN_DATE, A.CYCLE_END_DATE, A.STATE, A.DEBT_DATE, A.SP_ID, A.RUN_DATE, A.POSTING_DATE, A.INVOICE_DATE, A.ORIGIN_DATE FROM BILLING_CYCLE A WHERE A.CYCLE_BEGIN_DATE <= SYSDATE AND A.CYCLE_END_DATE > SYSDATE AND A.BILLING_CYCLE_TYPE_ID = :billingCycleTypeId", nativeQuery = true)
  List<Object[]> selectCurBillingCycleByBillingCycleTypeId (@Param("billingCycleTypeId") Long billingCycleTypeId);

  @Query(value = """
    SELECT BILLING_CYCLE_ID_SEQ.NEXTVAL FROM dual
    """, nativeQuery = true)
  Integer getNextBillingCycleId ();

  @Query(value = """
    SELECT MAX(b.cycle_end_date) AS cycleEndDate, MAX(b.debt_date) AS debtDate FROM billing_cycle b WHERE b.billing_cycle_type_id = :billingCycleTypeId
    """, nativeQuery = true)
  Optional<BillingCycleLastProjection> findLastByBillingCycleTypeId (@Param("billingCycleTypeId") Integer billingCycleTypeId);

  @Query("""
    SELECT COUNT(b) FROM BillingCycle b WHERE b.billingCycleTypeId = :billingCycleTypeId AND b.cycleEndDate > CURRENT_DATE
    """)
  Integer countActiveBillingCycles (@Param("billingCycleTypeId") Integer billingCycleTypeId);

  @Modifying
  @Transactional
  @Query("""
    DELETE FROM BillingCycle b WHERE b.billingCycleTypeId = :billingCycleTypeId AND b.cycleBeginDate > CURRENT_DATE
    """)
  int deleteFutureBillingCycles (@Param("billingCycleTypeId") Integer billingCycleTypeId);

  @Modifying
  @Transactional
  @Query("""
    DELETE FROM BillingCycle b WHERE b.billingCycleId = :billingCycleId
    """)
  int deleteByBillingCycleId (@Param("billingCycleId") Integer billingCycleId);

  @Query(value = """
    SELECT * FROM (
        SELECT B.* 
        FROM BILLING_CYCLE B
        WHERE B.BILLING_CYCLE_ID > :billingCycleId
          AND B.BILLING_CYCLE_TYPE_ID = :billingCycleTypeId
        ORDER BY B.BILLING_CYCLE_ID ASC
    ) WHERE ROWNUM <= 1
    """, nativeQuery = true)
  BillingCycle findNextBillingCycle (@Param("billingCycleId") Integer billingCycleId,
                                     @Param("billingCycleTypeId") Integer billingCycleTypeId);

  @Modifying
  @Transactional
  @Query(value = """
    DELETE FROM BILLING_CYCLE 
    WHERE CYCLE_BEGIN_DATE > SYSDATE 
    AND BILLING_CYCLE_ID = :billingCycleId
    """, nativeQuery = true)
  int deleteByBillingCycleIdIfFuture (@Param("billingCycleId") Integer billingCycleId);


  @Query(value = """
    SELECT * FROM (
        SELECT A.*
        FROM BILLING_CYCLE A
        WHERE A.BILLING_CYCLE_ID < :billingCycleId
          AND A.BILLING_CYCLE_TYPE_ID = :billingCycleTypeId
        ORDER BY A.BILLING_CYCLE_ID DESC
    ) WHERE ROWNUM = 1
    """, nativeQuery = true)
  BillingCycle findPreviousBillingCycle (
    @Param("billingCycleId") Integer billingCycleId,
    @Param("billingCycleTypeId") Integer billingCycleTypeId);

  @Query(value = """
    SELECT A.BILLING_CYCLE_ID AS billingCycleId, A.BILLING_CYCLE_TYPE_ID AS billingCycleTypeId, A.CYCLE_BEGIN_DATE AS cycleBeginDate, A.CYCLE_END_DATE AS cycleEndDate, A.STATE AS state, A.DEBT_DATE AS debtDate, A.SP_ID AS spId, A.RUN_DATE AS runDate FROM BILLING_CYCLE A JOIN ACCT B ON A.BILLING_CYCLE_TYPE_ID = B.BILLING_CYCLE_TYPE_ID WHERE A.CYCLE_BEGIN_DATE <= SYSDATE AND A.CYCLE_END_DATE > SYSDATE AND B.ACCT_ID = :acctId
    """,
    nativeQuery = true)
  Optional<SelectCurBillingCycleIDByAcctIdProjection> selectCurBillingCycleIDByAcctId (@Param("acctId") Long acctId);

  @Query(value = """
    SELECT 
        A.BILLING_CYCLE_ID AS billingCycleId
    FROM BILLING_CYCLE A
    JOIN ACCT B
        ON A.BILLING_CYCLE_TYPE_ID = B.BILLING_CYCLE_TYPE_ID
    WHERE A.CYCLE_BEGIN_DATE <= SYSDATE
      AND A.CYCLE_END_DATE > SYSDATE
      AND B.ACCT_ID = :acctId
    """, nativeQuery = true)
  Optional<SelectCurBillingCycleIDByAcctIdProjection> selectCurBillingCycleId (@Param("acctId") Long acctId);

  @Query(value = """
        SELECT
            BILLING_CYCLE_ID       AS billingCycleId,
            BILLING_CYCLE_TYPE_ID  AS billingCycleTypeId,
            CYCLE_BEGIN_DATE       AS cycleBeginDate,
            CYCLE_END_DATE         AS cycleEndDate,
            STATE                  AS state,
            DEBT_DATE              AS debtDate,
            SP_ID                  AS spId,
            RUN_DATE               AS runDate,
            POSTING_DATE           AS postingDate,
            INVOICE_DATE           AS invoiceDate,
            ORIGIN_DATE            AS originDate
        FROM BILLING_CYCLE
        WHERE BILLING_CYCLE_ID = :billingCycleId
        """,
        nativeQuery = true)
    Optional<SelectBillingCycleProjection> selectBillingCycle(
            @Param("billingCycleId") Long billingCycleId);
}


