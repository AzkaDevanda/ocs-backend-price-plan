package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcctDepositBal;
import com.sts.sinorita.projection.balanceAdjustment.SelectAcctDepositBalByAcctIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AcctDepositBalRepository extends JpaRepository<AcctDepositBal, Long> {
  @Query(value = "SELECT A.DEPOSIT_ITEM_ID AS depositItemId, A.ACCT_ID AS acctId, A.DEPOSIT_TYPE_ID AS depositTypeId, NVL(A.BAL, 0) AS bal, A.RESERVE_BAL AS reserveBal FROM ACCT_DEPOSIT_BAL A WHERE A.ACCT_ID = :acctId AND (NVL(A.BAL, 0) + A.RESERVE_BAL) < 0 AND A.EFF_DATE <= SYSDATE AND (A.EXP_DATE IS NULL OR A.EXP_DATE > SYSDATE) ORDER BY A.EXP_DATE ASC", nativeQuery = true)
  List<SelectAcctDepositBalByAcctIdProjection> selectAcctDepositBalByAcctId (@Param("acctId") Long acctId);

  @Transactional
  @Modifying
  @Query(value = """
    UPDATE ACCT_DEPOSIT_BAL
    SET RESERVE_BAL = RESERVE_BAL + :reserveBal
    WHERE DEPOSIT_ITEM_ID = :depositItemId
    """,
    nativeQuery = true)
  void updateAcctDepositBal (
    @Param("reserveBal") Long reserveBal,
    @Param("depositItemId") Long depositItemId
  );
}
