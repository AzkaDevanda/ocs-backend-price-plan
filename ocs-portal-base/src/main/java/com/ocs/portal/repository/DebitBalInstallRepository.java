package com.ocs.portal.repository;

import com.ocs.portal.entity.DebitBalInstall;
import com.ocs.portal.entity.DebitBalInstallId;
import com.ocs.portal.projection.balanceAdjustment.SelectDebitBalInstallProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DebitBalInstallRepository extends JpaRepository<DebitBalInstall, DebitBalInstallId> {

  @Query(value = "SELECT A.ACCT_ID, A.BAL, A.SP_ID, A.COMMISSION_CHARGE, A.RET_CHARGE, A.COMM_RET_CHARGE, A.LOAN_TYPE, A.INSTALL_SEQ, A.LAST_RET_DATE FROM DEBIT_BAL_INSTALL A WHERE A.ACCT_ID = :acctId AND (:loanType IS NULL OR A.LOAN_TYPE = :loanType) AND (:filterFlag = 'N' OR (A.BAL != -A.RET_CHARGE OR A.COMMISSION_CHARGE != -A.COMM_RET_CHARGE)) ORDER BY A.INSTALL_SEQ", nativeQuery = true)
  List<SelectDebitBalInstallProjection> selectDebitBalInstallByLoanType (@Param("acctId") Long acctId, @Param("loanType") String loanType, @Param("filterFlag") boolean filterFlag);

  @Transactional
  @Modifying
  @Query(value = "UPDATE DEBIT_BAL_INSTALL SET BAL = :bal, COMMISSION_CHARGE = :commissionCharge, RET_CHARGE = :retCharge, COMM_RET_CHARGE = :commRetCharge, LAST_RET_DATE = :lastRetDate WHERE ACCT_ID = :acctId AND LOAN_TYPE = :loanType AND INSTALL_SEQ = :installSeq", nativeQuery = true)
  void updateDebitBalInstall (@Param("bal") Long bal, @Param("commissionCharge") Long commissionCharge, @Param("retCharge") Long retCharge, @Param("commRetCharge") Long commRetCharge, @Param("lastRetDate") LocalDateTime lastRetDate, @Param("acctId") Long acctId, @Param("loanType") String loanType, @Param("installSeq") Long installSeq);
}
